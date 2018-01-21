package org.nineteens.ballwar.app;

import org.nineteens.ballwar.app.manager.SharedPreferencesManager;
import org.nineteens.ballwar.app.model.AppConstant;
import org.nineteens.ballwar.app.model.Role;
import org.nineteens.ballwar.app.model.Room;
import org.nineteens.ballwar.app.model.User;
import org.nineteens.ballwar.app.util.SerializeUtil;

import android.app.Application;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;

public class BallWarApplication extends Application {
	private User user;
	private Role role;
	private Room room;

	private SharedPreferencesManager spm;

	@Override
	public void onCreate() {
		super.onCreate();
		spm = new SharedPreferencesManager(getSharedPreferences(AppConstant.SP_DEFAULT, MODE_PRIVATE));
		initRoleInfo();
		initRoomInfo();
	}

	/**
	 * 获取缓存中的Room信息
	 */
	private void initRoomInfo() {
		room = spm.getData(AppConstant.SP_KEY_ROOM, Room.class);
	}

	/**
	 * 获取缓存中的Role信息
	 */
	private void initRoleInfo() {
		role = spm.getData(AppConstant.SP_KEY_ROLE, Role.class,new SharedPreferencesManager.Operator<Role>() {
			
			@Override
			public Role oper(SharedPreferences sp) {
				String roleName = "u" + System.currentTimeMillis();
				Role r = new Role();
				r.setName(roleName);
				Editor edit = sp.edit();
				edit.putString(AppConstant.SP_KEY_ROLE, SerializeUtil.toJson(role));
				edit.commit();
				return r;
			}
		});
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public Role getRole() {
		return role;
	}

	public void setRole(Role role) {
		this.role = role;
	}

	public Room getRoom() {
		return room;
	}

	public void setRoom(Room room) {
		this.room = room;
	}

	public SharedPreferencesManager getSpm() {
		return spm;
	}

	public void setSpm(SharedPreferencesManager spm) {
		this.spm = spm;
	}

}
