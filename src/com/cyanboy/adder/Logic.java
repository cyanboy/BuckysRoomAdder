package com.cyanboy.adder;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.firefox.FirefoxDriver;


public class Logic {
	WebDriver browser;
	
	WebElement emailForm;
	WebElement passwForm;
	WebElement loginSubmit;
	
	public Logic(String email, String passw) {
		browser = new FirefoxDriver();
		
		browser.get("http://buckysroom.org");
		
		emailForm = browser.findElement(By.name("email"));
		passwForm = browser.findElement(By.name("password"));
		loginSubmit = browser.findElement(By.name("login_submit"));
		
		execute(email, passw);
	}
	
	private void execute(String email, String passw) {
		emailForm.sendKeys(email);
		passwForm.sendKeys(passw);
		
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
			browser.get("https://buckysroom.org/search.php?page=" + i);
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
