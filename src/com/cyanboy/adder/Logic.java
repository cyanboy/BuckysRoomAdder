package com.cyanboy.adder;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.firefox.FirefoxDriver;

public class Logic {
	private WebDriver browser;
	
	private WebElement loginForm;
	private WebElement emailInput;
	private WebElement passwInput;
	
	private WebElement loginSubmit;
	
	private String passw;
	private String email;
	
	public Logic(String email, String passw) {
		browser = new FirefoxDriver();
		browser.get("http://buckysroom.org/register.php");
		
		
		loginForm = browser.findElement(By.id("loginform"));
		
		emailInput = loginForm.findElement(By.name("email"));
		passwInput = loginForm.findElement(By.name("password"));
		loginSubmit = loginForm.findElement(By.name("login_submit"));
		
		this.email = email;
		this.passw = passw;
		
		run();
	}
	
	public void run() {
		emailInput.sendKeys(email);
		passwInput.sendKeys(passw);
		
		try {
			Thread.sleep(1000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}

		loginSubmit.click();
		
		browser.get("https://buckysroom.org/search.php");
		
		String payload = "var links = document.getElementsByTagName('a'); for (var i = 0; i < links.length; i++) { if (links[i].innerHTML == 'Send Friend Request') { links[i].click(); } }";
		
		int lastPage = Integer.parseInt((String) ((JavascriptExecutor)browser).executeScript("var links = document.getElementsByTagName('a'); for (var i = 0; i < links.length; i++) { if (links[i].innerHTML == 'Next') { return links[i-1].innerHTML } } "));
		
		//System.out.println(lastPage);
		for (int i = 1; i <= lastPage; ++i) {
			browser.get("https://buckysroom.org/search.php?type=0&sort=pop&page=" + i);
			((JavascriptExecutor)browser).executeScript(payload);
			try {
				Thread.sleep(5000);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
		browser.quit();
	}
	
}
