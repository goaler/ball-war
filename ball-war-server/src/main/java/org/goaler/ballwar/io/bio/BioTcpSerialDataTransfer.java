package org.goaler.ballwar.io.bio;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.net.Socket;

import org.goaler.ballwar.io.DataTransfer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class BioTcpSerialDataTransfer implements DataTransfer {
	private static final Logger log = LoggerFactory.getLogger(BioTcpSerialDataTransfer.class);
	
	private Socket socket;
	private ObjectOutputStream oos;
	private ObjectInputStream ois;
	
	public BioTcpSerialDataTransfer(Socket socket) throws IOException{
		this.socket = socket;
		oos = new ObjectOutputStream(socket.getOutputStream());
		ois = new ObjectInputStream(socket.getInputStream());
	}

	@Override
	public boolean output(Serializable serial) {
		try {
			oos.writeObject(serial);
			return true;
		} catch (IOException e) {
			log.info("数据发送失败！{}",e.getMessage());
		}
		return false;
	}

	@Override
	public <T> T input(Class<T> clazz) {
		try {
			Object obj = ois.readObject();
			if (clazz.isInstance(obj)) {
				return clazz.cast(obj);
			}
//			if (clazz.isAssignableFrom(obj.getClass())) {
//				return clazz.cast(obj);
//			}
		} catch (ClassNotFoundException e) {
			log.info("接收数据失败！{}",e.getMessage());
		} catch (IOException e) {
			log.info("接收数据失败！{}",e.getMessage());
		}
		return null;
	}
	
	public String getIp(){
		return socket.getInetAddress().getHostAddress();
	}

}