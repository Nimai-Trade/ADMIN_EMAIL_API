package com.nimai.email.constants;

import java.util.HashMap;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.nimai.email.dao.ConfigurationDAO;

/**
 *
 * @author sahadeo.naik
 */
@Component
public class ConfigurationValues {
	final static Logger logger = LoggerFactory.getLogger(ConfigurationValues.class);
	private boolean loaded = false;
	private HashMap<String, String> hmapString = null;
	private HashMap<String, Integer> hmapInt = null;
	private HashMap<String, String> hmapEncrypted = null;
	private HashMap<String, Integer> hmapType = null;

	@Autowired
	ConfigurationDAO cdao;

	public void initializeConfigurationValues() {
		loadvalues();
	}

	private void setValues(ConfigurationValues object) {
		this.setHmapType(object.getHmapType());
		this.setHmapEncrypted(object.getHmapEncrypted());
		this.setHmapInt(object.getHmapInt());
		this.setHmapString(object.getHmapString());
	}

	public String getStringValues(String type, String key) {
		if (!loaded) {
			ConfigurationValues object = loadvalues();
			setValues(object);
			loaded = true;
		}
		if (isreload()) {
			ConfigurationValues object = loadvalues();
			setValues(object);
			resetReload();
		}

		return getHmapString().get(type + key);
	}

	public int getIntegerValues(String type, String key) {
		if (!loaded) {
			ConfigurationValues object = loadvalues();
			setValues(object);
			loaded = true;
		}
		if (isreload()) {
			ConfigurationValues object = loadvalues();
			setValues(object);
			resetReload();
		}
		return getHmapInt().get(type + key);
	}

	private void resetReload() {
		cdao.reset();
	}

	private boolean isreload() {
		boolean reload = false;
		long n_reload = cdao.getNumberValue("ALL", "ALL.RELOAD");
		if (n_reload == Constants.CONFIGURATION_RELOAD) {
			reload = true;
		}
		return reload;
	}

	private ConfigurationValues loadvalues() {
		ConfigurationValues values = null;
		values = cdao.loadAllValues();
		return values;
	}

	/**
	 * @return the hmapString
	 */
	private HashMap<String, String> getHmapString() {
		return hmapString;
	}

	/**
	 * @param hmapString the hmapString to set
	 */
	public void setHmapString(HashMap<String, String> hmapString) {
		this.hmapString = hmapString;
	}

	/**
	 * @return the hmapInt
	 */
	private HashMap<String, Integer> getHmapInt() {
		return hmapInt;
	}

	/**
	 * @param hmapInt the hmapInt to set
	 */
	public void setHmapInt(HashMap<String, Integer> hmapInt) {
		this.hmapInt = hmapInt;
	}

	/**
	 * @return the hmapEncrypted
	 */
	private HashMap<String, String> getHmapEncrypted() {
		return hmapEncrypted;
	}

	/**
	 * @param hmapEncrypted the hmapEncrypted to set
	 */
	public void setHmapEncrypted(HashMap<String, String> hmapEncrypted) {
		this.hmapEncrypted = hmapEncrypted;
	}

	/**
	 * @return the hmapType
	 */
	private HashMap<String, Integer> getHmapType() {
		return hmapType;
	}

	/**
	 * @param hmapType the hmapType to set
	 */
	public void setHmapType(HashMap<String, Integer> hmapType) {
		this.hmapType = hmapType;
	}
}
