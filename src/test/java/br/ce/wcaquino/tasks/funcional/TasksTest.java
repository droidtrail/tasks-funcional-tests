package br.ce.wcaquino.tasks.funcional;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.concurrent.TimeUnit;

import org.junit.Assert;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;

public class TasksTest {

	public WebDriver acessarAplicacao() throws MalformedURLException {
		
//		WebDriver driver = new ChromeDriver();
		DesiredCapabilities cap = DesiredCapabilities.chrome();
		WebDriver driver = new RemoteWebDriver(new URL("http://192.168.56.1:4444/wd/hub"),cap);
		driver.navigate().to("http://192.168.56.1:8001/tasks");
		driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
		return driver;
	}

	@Test
	public void deveSalvarTarefaComSucesso() throws MalformedURLException {
		WebDriver driver = acessarAplicacao();
		try {
			// Clicar em Add Todo
			driver.findElement(By.id("addTodo")).click();
			// Escrever descrição
			driver.findElement(By.id("task")).sendKeys("Teste via Selenium");
			// Escrever a data
			driver.findElement(By.id("dueDate")).sendKeys("10/10/2020");
			// Clicar em Salvar
			driver.findElement(By.id("saveButton")).click();
			// Validar mensagem de sucesso
			String mensagemSucesso = driver.findElement(By.id("message")).getText();
			Assert.assertEquals("Success!", mensagemSucesso);
		} finally {
			driver.quit();
		}
	}

	@Test
	public void naoDeveSalvarTarefaSemDescricao() throws MalformedURLException {
		WebDriver driver = acessarAplicacao();
		try {
			// Clicar em Add Todo
			driver.findElement(By.id("addTodo")).click();
			// Escrever a data
			driver.findElement(By.id("dueDate")).sendKeys("10/10/2021");
			// Clicar em Salvar
			driver.findElement(By.id("saveButton")).click();
			// Validar mensagem de sucesso
			String mensagemSucesso = driver.findElement(By.id("message")).getText();
			Assert.assertEquals("Fill the task description", mensagemSucesso);
		} finally {
			driver.quit();
		}
	}

	@Test
	public void nãoDeveSalvarTarefaSemData() throws MalformedURLException {
		WebDriver driver = acessarAplicacao();
		try {
			// Clicar em Add Todo
			driver.findElement(By.id("addTodo")).click();
			// Escrever descrição
			driver.findElement(By.id("task")).sendKeys("Teste via Selenium");
			// Clicar em Salvar
			driver.findElement(By.id("saveButton")).click();
			// Validar mensagem de sucesso
			String mensagemSucesso = driver.findElement(By.id("message")).getText();
			Assert.assertEquals("Fill the due date", mensagemSucesso);
		} finally {
			driver.quit();
		}
	}
	
	@Test
	public void deveSalvarTarefaComDataPassada() throws MalformedURLException {
		WebDriver driver = acessarAplicacao();
		try {
			// Clicar em Add Todo
			driver.findElement(By.id("addTodo")).click();
			// Escrever descrição
			driver.findElement(By.id("task")).sendKeys("Teste via Selenium");
			// Escrever a data
			driver.findElement(By.id("dueDate")).sendKeys("10/10/2010");
			// Clicar em Salvar
			driver.findElement(By.id("saveButton")).click();
			// Validar mensagem de sucesso
			String mensagemSucesso = driver.findElement(By.id("message")).getText();
			Assert.assertEquals("Due date must not be in past", mensagemSucesso);
		} finally {
			driver.quit();
		}
	}
}
