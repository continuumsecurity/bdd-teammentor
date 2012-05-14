/*******************************************************************************
 *    BDD-Security, application security testing framework
 * 
 * Copyright (C) `2012 Stephen de Vries`
 * 
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU Affero General Public License as
 * published by the Free Software Foundation, either version 3 of the
 * License, or (at your option) any later version.
 * 
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU Affero General Public License for more details.
 * 
 * You should have received a copy of the GNU Affero General Public License
 * along with this program.  If not, see `<http://www.gnu.org/licenses/>`.
 ******************************************************************************/
package net.continuumsecurity.web;

import net.continuumsecurity.Config;
import net.continuumsecurity.UnexpectedContentException;
import net.continuumsecurity.behaviour.ICaptcha;
import net.continuumsecurity.web.drivers.DriverFactory;
import org.apache.log4j.Logger;
import org.openqa.selenium.Cookie;
import org.openqa.selenium.WebDriver;



public class WebApplication extends Application {
	protected WebDriver driver;
	protected ICaptchaHelper captchaHelper;

	public WebApplication(WebDriver driver) {
		log = Logger.getLogger(WebApplication.class);
		log.debug("Constructing new WebApplication");
		this.driver = driver;
		if (this instanceof ICaptcha) captchaHelper = new CaptchaHelper((ICaptcha)this);
	}

    public ICaptchaHelper getCaptchaHelper() {
        return captchaHelper;
    }

    public void setCaptchaHelper(ICaptchaHelper captchaHelper) {
        this.captchaHelper = captchaHelper;
    }

	public WebDriver getDriver() {
		return driver;
	}

	public void setDriver(WebDriver driver) {
		this.driver = driver;
	}

    public void verifyTextPresent(String text) {
        if (!this.driver.getPageSource().contains(text)) throw new UnexpectedContentException("Expected text: ["+text+"] was not found.");
    }

    @Override
    public Cookie getCookieByName(String name) {
        return driver.manage().getCookieNamed(name);
    }

    @Override
    public void enableHttpLoggingClient() {
        setDriver(DriverFactory.getDriver(Config.getBurpDriver()));
    }

    @Override
    public void enableDefaultClient() {
        setDriver(DriverFactory.getDriver(Config.getDefaultDriver()));
    }
}
