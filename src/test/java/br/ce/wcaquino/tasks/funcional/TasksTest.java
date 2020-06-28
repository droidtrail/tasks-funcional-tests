package br.ce.wcaquino.tasks.funcional;

import java.util.concurrent.TimeUnit;

import org.junit.Assert;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;

public class TasksTest {

	public WebDriver acessarAplicacao() {
		WebDriver driver = new ChromeDriver();
		driver.navigate().to("http://localhost:8001/tasks");
		driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
		return driver;
	}

	@Test
	public void deveSalvarTarefaComSucesso() {
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
	public void naoDeveSalvarTarefaSemDescricao() {
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
	public void nãoDeveSalvarTarefaSemData() {
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
	public void deveSalvarTarefaComDataPassada() {
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
