package com.sdi.tests.Tests;

import static org.junit.Assert.*;

import java.io.File;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.junit.After;
import org.junit.Before;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runners.MethodSorters;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.firefox.FirefoxBinary;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxProfile;

import com.sdi.tests.pageobjects.PO_FormEditarTarea;
import com.sdi.tests.pageobjects.PO_FormLogin;
import com.sdi.tests.pageobjects.PO_FormRegistro;
import com.sdi.tests.pageobjects.PO_FormCrearTarea;
import com.sdi.tests.utils.SeleniumUtils;

//Ordenamos las pruebas por el nombre del método
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class PlantillaSDI2_Tests1617 {

	WebDriver driver;
	List<WebElement> elementos = null;
	boolean isInterno = true;

	private String localhost() {
		return "http://localhost:8180";
	}

	private void reiniciarBBDD() {
		// Nos logueamos como administrador
		new PO_FormLogin().rellenaFormulario(driver, "admin1", "admin1");
		SeleniumUtils.esperaCargaPaginaxpath(driver, "/html/body/form[1]/div/ul/li[2]/a/span[1]",4);
		// Reiniciamos la BBDD
		SeleniumUtils.clickSubopcionMenuHover(driver, "menu1:BBDD",
				"menu1:ReiniciarBBDD");
		SeleniumUtils.esperaCargaPaginaxpath(driver, "/html/body/form[1]/div/ul/li[1]/a/span[1]",4);
		// Cerramos sesion con el admin
		SeleniumUtils.clickSubopcionMenuHover(driver, "menu1:gestionSesion",
				"menu1:cerrarSesion");
	}

	public PlantillaSDI2_Tests1617() {
	}

	@Before
	public void run() {
		// Este código es para ejecutar con la versión portale de Firefox 46.0
		File pathToBinary = new File("S:\\firefox\\FirefoxPortable.exe");
		FirefoxBinary ffBinary = new FirefoxBinary(pathToBinary);
		FirefoxProfile firefoxProfile = new FirefoxProfile();
		driver = new FirefoxDriver(ffBinary, firefoxProfile);
		driver.get(localhost() + "/sdi2-56");
		// Siempre iniciamos las pruebas con una base de datos limpia
		reiniciarBBDD();
		// Este código es para ejecutar con una versión instalada de Firex 46.0
		// driver = new FirefoxDriver();
		// driver.get("http://localhost:8180/sdi2-n");
	}

	@After
	public void end() {
		// Cerramos el navegador
		// Si se quieren cerrar todas las ventanas una vez terminen:
		driver.quit();
	}

	// PRUEBAS
	// ADMINISTRADOR
	// PR01: Autentificar correctamente al administrador.
	@Test
	public void prueba01() {
		new PO_FormLogin().rellenaFormulario(driver, "admin1", "admin1");

		// Encontrar elemento de la siguiente vista
		elementos = SeleniumUtils.esperaCargaPagina(driver, "id",
				"form-listado", 6);
		assertTrue(elementos != null);
	}

	// PR02: Fallo en la autenticación del administrador por introducir mal el
	// login.
	@Test
	public void prueba02() {
		new PO_FormLogin()
				.rellenaFormulario(driver, "administrador?", "admin1");

		// Encontrar elemento de la siguiente vista
		elementos = SeleniumUtils.esperaCargaPagina(driver, "id", "enviarUser",
				6);
		assertTrue(elementos != null);
	}

	// PR03: Fallo en la autenticación del administrador por introducir mal la
	// password.
	@Test
	public void prueba03() {
		new PO_FormLogin().rellenaFormulario(driver, "admin1", "administrador");

		// Encontrar elemento de la siguiente vista
		elementos = SeleniumUtils.esperaCargaPagina(driver, "id", "enviarUser",
				6);
		assertTrue(elementos != null);
	}

	// PR04: Probar que la base de datos contiene los datos insertados con
	// conexión correcta a la base de datos.
	@Test
	public void prueba04() {
		new PO_FormLogin().rellenaFormulario(driver, "user1", "user1");

		// Hacemos clic en crear tarea
		SeleniumUtils
				.esperaCargaPagina(driver, "id", "menu1:gestionSesion", 10);
		SeleniumUtils.clickSubopcionMenuHover(driver, "menu1:gestionTareas",
				"menu1:tareas");

		// Rellenamos el formulario
		new PO_FormCrearTarea().rellenaFormulario(driver, "NuevaTareaSinCategoria",
				"Tarea sin categoria", "31/05/2017  08:28:35",
				"Category_1");
		
		SeleniumUtils
		.esperaCargaPaginaxpath(driver, "/html/body/form[3]/div[2]", 5);
		SeleniumUtils.clickSubopcionMenuHover(driver, "menu1:gestionSesion", "menu1:cerrarSesion");
		reiniciarBBDD();
		new PO_FormLogin().rellenaFormulario(driver, "admin1", "admin1");
		List<WebElement> filas = new ArrayList<WebElement>();
		for(int i =0;i<3;i++){
			filas.add( SeleniumUtils.esperaCargaPagina(driver,"id","form-listado:tabla:"+i + ":login", 4).get(0));
		}
		assertEquals(3,filas.size());
		SeleniumUtils.clickSubopcionMenuHover(driver, "menu1:gestionSesion", "menu1:cerrarSesion");
		new PO_FormLogin().rellenaFormulario(driver, "user1", "user1");
		filas = new ArrayList<WebElement>();
		for(int i =0;i<8;i++){
			filas.add(SeleniumUtils.esperaCargaPagina(driver,"id","tablaDelUsuario:tablaTareas:" + i +":title",5).get(0));
		}
		SeleniumUtils.esperaCargaPagina(driver, "class", "ui-paginator-next", 4).get(0).click();
		for(int i =8;i<10;i++){
			filas.add(SeleniumUtils.esperaCargaPagina(driver,"id","tablaDelUsuario:tablaTareas:" + i +":title",5).get(0));
		}
		assertEquals(10,filas.size());
		
		SeleniumUtils.clickSubopcionMenuHover(driver, "menu1:gestionSesion", "menu1:cerrarSesion");
		new PO_FormLogin().rellenaFormulario(driver, "user2", "user2");
		filas = new ArrayList<WebElement>();
		for(int i =0;i<8;i++){
			filas.add(SeleniumUtils.esperaCargaPagina(driver,"id","tablaDelUsuario:tablaTareas:" + i +":title",5).get(0));
		}
		SeleniumUtils.esperaCargaPagina(driver, "class", "ui-paginator-next", 4).get(0).click();
		for(int i =8;i<10;i++){
			filas.add(SeleniumUtils.esperaCargaPagina(driver,"id","tablaDelUsuario:tablaTareas:" + i +":title",5).get(0));
		}
		assertEquals(10,filas.size());
		
		SeleniumUtils.clickSubopcionMenuHover(driver, "menu1:gestionSesion", "menu1:cerrarSesion");
		new PO_FormLogin().rellenaFormulario(driver, "user3", "user3");
		filas = new ArrayList<WebElement>();
		for(int i =0;i<8;i++){
			filas.add(SeleniumUtils.esperaCargaPagina(driver,"id","tablaDelUsuario:tablaTareas:" + i +":title",5).get(0));
		}
		SeleniumUtils.esperaCargaPagina(driver, "class", "ui-paginator-next", 4).get(0).click();
		for(int i =8;i<10;i++){
			filas.add(SeleniumUtils.esperaCargaPagina(driver,"id","tablaDelUsuario:tablaTareas:" + i +":title",5).get(0));
		}
		assertEquals(10,filas.size());
		
		SeleniumUtils.clickSubopcionMenuHover(driver, "menu1:gestionSesion", "menu1:cerrarSesion");
		
	}

	// PR05: Visualizar correctamente la lista de usuarios normales.
	@Test
	public void prueba05() {
		new PO_FormLogin().rellenaFormulario(driver, "admin1", "admin1");

		// Encontrar elemento de la siguiente vista
		elementos = SeleniumUtils.esperaCargaPagina(driver, "id",
				"form-listado", 6);
		assertTrue(elementos != null);

		WebElement table = driver.findElement(By.id("form-listado:tabla"));

		List<WebElement> allRows = table.findElements(By.tagName("tr"));

		assertTrue(!allRows.isEmpty());

	}

	// PR06: Cambiar el estado de un usuario de ENABLED a DISABLED. Y tratar de
	// entrar con el usuario que se desactivado.
	@Test
	public void prueba06() {

		PO_FormLogin login = new PO_FormLogin();

		login.rellenaFormulario(driver, "admin1", "admin1");

		// Encontrar elemento de la siguiente vista
		elementos = SeleniumUtils.esperaCargaPagina(driver, "id",
				"form-listado", 6);
		assertTrue(elementos != null);

		SeleniumUtils.clickButton(driver, "form-listado:tabla:0:actDesact");

		SeleniumUtils.clickSubopcionMenuHover(driver, "menu1:gestionSesion",
				"menu1:cerrarSesion");

		// Nos logueamos
		login.rellenaFormulario(driver, "user1", "user1");

		SeleniumUtils.textoPresentePagina(driver, "mejor suerte en otra vida");

	}

	// PR07: Cambiar el estado de un usuario a DISABLED a ENABLED. Y Y tratar de
	// entrar con el usuario que se ha activado.
	@Test
	public void prueba07() {

		PO_FormLogin login = new PO_FormLogin();

		login.rellenaFormulario(driver, "admin1", "admin1");

		// Encontrar elemento de la siguiente vista
		elementos = SeleniumUtils.esperaCargaPagina(driver, "id",
				"form-listado", 6);
		assertTrue(elementos != null);

		SeleniumUtils.clickButton(driver, "form-listado:tabla:0:actDesact");

		SeleniumUtils.clickSubopcionMenuHover(driver, "menu1:gestionSesion",
				"menu1:cerrarSesion");

		// Nos logueamos como user1
		login.rellenaFormulario(driver, "user1", "user1");

		SeleniumUtils.esperaCargaPagina(driver, "id", "loginForm:user", 10);

		SeleniumUtils.textoPresentePagina(driver, "mejor suerte en otra vida");

		// Nos logueamos como admin
		login.rellenaFormulario(driver, "admin1", "admin1");

		// Encontrar elemento de la siguiente vista
		elementos = SeleniumUtils.esperaCargaPagina(driver, "id",
				"form-listado", 6);
		assertTrue(elementos != null);

		SeleniumUtils.clickButton(driver, "form-listado:tabla:0:actDesact");

		SeleniumUtils.clickSubopcionMenuHover(driver, "menu1:gestionSesion",
				"menu1:cerrarSesion");

		// Nos logueamos como user1
		login.rellenaFormulario(driver, "user1", "user1");

		elementos = SeleniumUtils.esperaCargaPagina(driver, "id",
				"tablaDelUsuario:tablaTareas", 6);
		assertTrue(elementos != null);
	}

	// PR08: Ordenar por Login
	@Test
	public void prueba08() throws InterruptedException {
		new PO_FormLogin().rellenaFormulario(driver, "admin1", "admin1");

		// Encontrar elemento de la siguiente vista
		elementos = SeleniumUtils.esperaCargaPagina(driver, "id",
				"form-listado", 6);
		assertTrue(elementos != null);

		List<WebElement> elementos = SeleniumUtils.esperaCargaPagina(driver,
				"class", "sortable-column-icon", 2);
		Thread.sleep(500);
		elementos.get(0).click();

		Thread.sleep(500);
		boolean u1 = driver.findElement(By.id("form-listado:tabla:0:login"))
				.getText().equals("user1");
		boolean u2 = driver.findElement(By.id("form-listado:tabla:1:login"))
				.getText().equals("user2");
		boolean u3 = driver.findElement(By.id("form-listado:tabla:2:login"))
				.getText().equals("user3");
		assertTrue(u1 && u2 && u3);

		Thread.sleep(500);
		elementos.get(0).click();
		Thread.sleep(500);

		u3 = driver.findElement(By.id("form-listado:tabla:0:login")).getText()
				.equals("user3");
		u2 = driver.findElement(By.id("form-listado:tabla:1:login")).getText()
				.equals("user2");
		u1 = driver.findElement(By.id("form-listado:tabla:2:login")).getText()
				.equals("user1");

		assertTrue(u1 && u2 && u3);
	}

	// PR09: Ordenar por Email
	@Test
	public void prueba09() throws InterruptedException {
		new PO_FormLogin().rellenaFormulario(driver, "admin1", "admin1");

		// Encontrar elemento de la siguiente vista
		elementos = SeleniumUtils.esperaCargaPagina(driver, "id",
				"form-listado", 6);
		assertTrue(elementos != null);

		List<WebElement> elementos = SeleniumUtils.esperaCargaPagina(driver,
				"class", "sortable-column-icon", 2);
		Thread.sleep(500);
		elementos.get(1).click();

		Thread.sleep(500);
		boolean u1 = driver.findElement(By.id("form-listado:tabla:0:email"))
				.getText().equals("user1@email.com");
		boolean u2 = driver.findElement(By.id("form-listado:tabla:1:email"))
				.getText().equals("user2@email.com");
		boolean u3 = driver.findElement(By.id("form-listado:tabla:2:email"))
				.getText().equals("user3@email.com");
		assertTrue(u1 && u2 && u3);

		Thread.sleep(500);
		elementos.get(1).click();
		Thread.sleep(500);

		u3 = driver.findElement(By.id("form-listado:tabla:0:email")).getText()
				.equals("user3@email.com");
		u2 = driver.findElement(By.id("form-listado:tabla:1:email")).getText()
				.equals("user2@email.com");
		u1 = driver.findElement(By.id("form-listado:tabla:2:email")).getText()
				.equals("user1@email.com");

		assertTrue(u1 && u2 && u3);
	}

	// PR10: Ordenar por Status
	@Test
	public void prueba10() throws InterruptedException {
		new PO_FormLogin().rellenaFormulario(driver, "admin1", "admin1");

		// Encontrar elemento de la siguiente vista
		elementos = SeleniumUtils.esperaCargaPagina(driver, "id",
				"form-listado", 6);
		assertTrue(elementos != null);

		List<WebElement> elementos = SeleniumUtils.esperaCargaPagina(driver,
				"class", "sortable-column-icon", 2);
		Thread.sleep(500);
		elementos.get(2).click();

		Thread.sleep(500);
		boolean u1 = driver.findElement(By.id("form-listado:tabla:0:status"))
				.getText().equals("ENABLED");
		boolean u2 = driver.findElement(By.id("form-listado:tabla:1:status"))
				.getText().equals("ENABLED");
		boolean u3 = driver.findElement(By.id("form-listado:tabla:2:status"))
				.getText().equals("ENABLED");
		assertTrue(u1 && u2 && u3);

		Thread.sleep(500);
		elementos.get(2).click();
		Thread.sleep(500);

		u3 = driver.findElement(By.id("form-listado:tabla:0:status")).getText()
				.equals("ENABLED");
		u2 = driver.findElement(By.id("form-listado:tabla:1:status")).getText()
				.equals("ENABLED");
		u1 = driver.findElement(By.id("form-listado:tabla:2:status")).getText()
				.equals("ENABLED");

		assertTrue(u1 && u2 && u3);
	}

	// PR11: Borrar una cuenta de usuario normal y datos relacionados.
	@Test
	public void prueba11() {
		// Nos logueamos como admin
		new PO_FormLogin().rellenaFormulario(driver, "admin1", "admin1");

		// Borramos el usuario
		SeleniumUtils.esperaCargaPagina(driver, "id",
				"form-listado:tabla:0:dialogEliminar", 10);
		SeleniumUtils.clickButton(driver, "form-listado:tabla:0:dialogEliminar");

		//Aceptamos
		SeleniumUtils.esperaCargaPagina(driver, "id",
				"form-eliminar:eliminar", 10);
		
		SeleniumUtils.clickButton(driver, "form-eliminar:eliminar");


		// Comprobamos que el usuario ya no existe
		SeleniumUtils.esperaCargaPagina(driver, "id", "form-listado:tabla", 10);
		SeleniumUtils.textoNoPresentePagina(driver, "user1");

		SeleniumUtils.clickSubopcionMenuHover(driver, "menu1:gestionSesion",
				"menu1:cerrarSesion");

		new PO_FormLogin().rellenaFormulario(driver, "user1", "user1");

		SeleniumUtils.textoPresentePagina(driver, "El usuario o contraseña"
				+ " introducido es erroneo, intentelo de nuevo");

		/*
		 * Las tareas y categorías no pueden existir sin un usuario, por lo que
		 * si el usuario no existe porque ha sido borrado, estas también han
		 * sido borradas y no existen.
		 */
	}

	// PR12: Crear una cuenta de usuario normal con datos válidos.
	@Test
	public void prueba12() {
		// Opcion de crear usuario
		SeleniumUtils.clickSubopcionMenuHover(driver, "menu1:gestionUsuarios",
				"menu1:registro");

		// Rellenamos el formulario con información correcta
		new PO_FormRegistro().rellenaFormulario(driver, "usuarioNuevo",
				"emailNuevo@mail.com", "NuevaContraseña123",
				"NuevaContraseña123");

		// Ahora deberíamos estar en index.xhtml
		SeleniumUtils.esperaCargaPagina(driver, "id", "loginForm:user", 10);
		SeleniumUtils.textoPresentePagina(driver, "Inicio de sesión");

		PO_FormLogin formLogin = new PO_FormLogin();
		formLogin.rellenaFormulario(driver, "usuarioNuevo",
				"NuevaContraseña123");

		// Se debería loguear correctamente, cerramos sesion.
		SeleniumUtils
				.esperaCargaPagina(driver, "id", "menu1:gestionSesion", 10);
		SeleniumUtils.textoPresentePagina(driver, "usuarioNuevo");
		SeleniumUtils.clickSubopcionMenuHover(driver, "menu1:gestionSesion",
				"menu1:cerrarSesion");

		// Nos logueamos como admin
		formLogin.rellenaFormulario(driver, "admin1", "admin1");
		SeleniumUtils.textoPresentePagina(driver, "usuarioNuevo");

		// Comprobamos que existe el usuario y se lista correctamente
		SeleniumUtils.esperaCargaPagina(driver, "id", "form-listado:tabla", 10);
		SeleniumUtils.textoPresentePagina(driver, "usuarioNuevo");
		SeleniumUtils.textoPresentePagina(driver, "emailNuevo@mail.com");
	}

	// PR13: Crear una cuenta de usuario normal con login repetido.
	@Test
	public void prueba13() {
		// Opcion de crear usuario
		SeleniumUtils.clickSubopcionMenuHover(driver, "menu1:gestionUsuarios",
				"menu1:registro");

		// Rellenamos el formulario con información incorrecta.
		// Login ya existente en la BBDD
		new PO_FormRegistro().rellenaFormulario(driver, "user1",
				"emailNuevo@mail.com", "NuevaContraseña123",
				"NuevaContraseña123");

		// Ahora deberíamos estar en registro.xhtml
		SeleniumUtils.esperaCargaPagina(driver, "id", "form-registro:mensajeLogin", 10);
		SeleniumUtils.textoPresentePagina(driver, "user1 ese usuario ya "
				+ "existe en el sistema");
		
		// Vamos a index.xhtml
		driver.get(localhost() + "/sdi2-56");

		// Nos logueamos como admin
		new PO_FormLogin().rellenaFormulario(driver, "admin1", "admin1");

		// Comprobamos que existe el usuario y se lista correctamente
		SeleniumUtils.esperaCargaPagina(driver, "id", "form-listado:tabla", 10);
		SeleniumUtils.textoPresentePagina(driver, "user1");
		SeleniumUtils.textoNoPresentePagina(driver, "emailNuevo@mail.com");
	}

	// PR14: Crear una cuenta de usuario normal con Email incorrecto.
	@Test
	public void prueba14() {
		// Opcion de crear usuario
		SeleniumUtils.clickSubopcionMenuHover(driver, "menu1:gestionUsuarios",
				"menu1:registro");

		// Rellenamos el formulario con información incorrecta.
		// Login ya existente en la BBDD
		new PO_FormRegistro().rellenaFormulario(driver, "usuarioNuevo",
				"emailErroneo", "NuevaContraseña123", "NuevaContraseña123");

		SeleniumUtils.textoPresentePagina(driver, "Registro de usuarios");
		/*
		 * Ahora deberíamos estar en registro.xhtml y debería mostrar un mensaje
		 * diciendo que el email no sigue los estandares de X@X.X
		 * 
		 * Si eso es asi, debería existir el siguiente textfield con la
		 * siguiente clase y el focus.
		 */
		List<WebElement> mensajeError = SeleniumUtils.esperaCargaPagina(driver,
				"class", "ui-inputfield ui-inputtext ui-widget "
						+ "ui-state-default ui-corner-all ui-state-focus", 10);
		assertFalse(mensajeError.isEmpty());

		// Vamos a index.xhtml
		driver.get(localhost() + "/sdi2-56");

		// Nos logueamos como admin
		new PO_FormLogin().rellenaFormulario(driver, "admin1", "admin1");

		// Comprobamos que existe el usuario y se lista correctamente
		SeleniumUtils.esperaCargaPagina(driver, "id", "form-listado:tabla", 10);
		SeleniumUtils.textoNoPresentePagina(driver, "usuarioNuevo");
		SeleniumUtils.textoNoPresentePagina(driver, "emailErroneo");
	}

	// PR15: Crear una cuenta de usuario normal con Password incorrecta.
	@Test
	public void prueba15() {
		// Opcion de crear usuario
		SeleniumUtils.clickSubopcionMenuHover(driver, "menu1:gestionUsuarios",
				"menu1:registro");

		// Rellenamos el formulario con información incorrecta.
		// Contraseña sin números
		new PO_FormRegistro().rellenaFormulario(driver, "usuarioNuevo",
				"emailNuevo@mail.com", "contraseñaSinNumeros",
				"contraseñaSinNumeros");

		/*
		 * Como la contraseña no tiene números saltará una excepción y se
		 * mostrará un mensaje en el que se informe de ello.
		 */
		SeleniumUtils.esperaCargaPagina(driver, "id", "form-registro:msg", 10);
		//SeleniumUtils.textoPresentePagina(driver, "no contiene letras y números");

		// Rellenamos el formulario con información incorrecta.
		// Contraseña corta
		new PO_FormRegistro().rellenaFormulario(driver, "usuarioNuevo",
				"emailNuevo@mail.com", "c", "c");

		/*
		 * Como la contraseña es demasiado pequeña salta excepción y se mostrará
		 * un mensaje en el que se informe de ello.
		 */
		SeleniumUtils.esperaCargaPagina(driver, "id", "form-registro:mensajePass", 10);
		SeleniumUtils.textoPresentePagina(driver, "el largo es inferior");

		// Rellenamos el formulario con información incorrecta.
		// Contraseña mal repetida
		new PO_FormRegistro().rellenaFormulario(driver, "usuarioNuevo",
				"emailNuevo@mail.com", "contraseñaValida123",
				"contraseñaNoRepetida123");

		/*
		 * Como las contraseñas son distintas salta excepción y se mostrará un
		 * mensaje en el que se informe de ello.
		 */
		SeleniumUtils.esperaCargaPagina(driver, "id", "form-registro:msg", 10);
		SeleniumUtils.textoPresentePagina(driver, "Las contraseñas no "
				+ "coinciden, intentelo de nuevo");

		// Vamos a index.xhtml
		driver.get(localhost() + "/sdi2-56");

		// Nos logueamos como admin
		new PO_FormLogin().rellenaFormulario(driver, "admin1", "admin1");

		// Comprobamos que existe el usuario y se lista correctamente
		SeleniumUtils.esperaCargaPagina(driver, "id", "form-listado:tabla", 10);
		SeleniumUtils.textoNoPresentePagina(driver, "usuarioNuevo");
		SeleniumUtils.textoNoPresentePagina(driver, "emailNuevo@mail.com");
	}

	// USUARIO
	// PR16: Comprobar que en Inbox sólo aparecen listadas las tareas sin
	// categoría y que son las que tienen que. Usar paginación navegando por las
	// tres páginas.
	@Test
	public void prueba16() throws InterruptedException{
		PO_FormLogin login = new PO_FormLogin();

		login.rellenaFormulario(driver, "user1", "user1");

		// Encontrar elemento de la siguiente vista
		elementos = SeleniumUtils.esperaCargaPagina(driver, "id",
				"botonesListas", 6);
		assertTrue(elementos != null);
		SeleniumUtils.esperaCargaPagina(driver, "id",
				"botonesListas:theInbox", 6).get(0).click();
		SeleniumUtils.esperaCargaPagina(driver,
				"id", "tablaDelUsuario:tablaTareas", 10);
		//SeleniumUtils.textoNoPresentePagina(driver, "Categoria");
		SeleniumUtils.esperaCargaPaginaNoTexto(driver, "Categoria", 5);
		//SeleniumUtils.textoNoPresentePagina(driver, "Categoria");
		SeleniumUtils.esperaCargaPaginaxpath(driver,
				"/html/body/form[3]/div[2]/div[1]/span[5]/span ", 5);
		SeleniumUtils.textoNoPresentePagina(driver, "Categoria");
		SeleniumUtils.esperaCargaPaginaxpath(driver,"/html/body/form[3]/div[2]/div[1]/span[4]/span[2]", 4).get(0).click();
		SeleniumUtils.textoNoPresentePagina(driver, "Categoria");
		SeleniumUtils.esperaCargaPaginaxpath(driver,"/html/body/form[3]/div[2]/div[1]/span[4]/span[2]", 4).get(0).click();
		SeleniumUtils.textoNoPresentePagina(driver, "Categoria");
		
	}

	// PR17: Funcionamiento correcto de la ordenación por fecha planeada.
	@Test
	public void prueba17(){
		new PO_FormLogin().rellenaFormulario(driver, "user1", "user1");

		SeleniumUtils.clickButton(driver, "botonesListas:theInbox");
		SeleniumUtils.esperaCargaPaginaxpath(driver, "/html/body/form[3]/div[2]", 5);
		
		// Encontrar elemento de la siguiente vista
		elementos = SeleniumUtils.esperaCargaPaginaxpath(driver, "/html/body/form[3]", 6);
		assertTrue(elementos != null);

		SeleniumUtils.esperaCargaPaginaxpath(driver,
				"/html/body/form[3]/div[2]/div[2]/table/thead/tr/th[3]/span[2]", 4).get(0).click();
		SeleniumUtils.esperaCargaPaginaxpath(driver,
				"/html/body/form[3]/div[2]/div[2]/table/thead/tr/th[3]/span[2]", 4).get(0).click();
		String tarea = SeleniumUtils.esperaCargaPaginaxpath(driver,
				"/html/body/form[3]/div[2]/div[2]/table/tbody/tr[1]/td[1]/label", 4).get(0).getText();
		assertTrue(tarea.equals("tarea1"));

	}

	// PR18: Funcionamiento correcto del filtrado.
	@Test
	public void prueba18() throws InterruptedException {

		new PO_FormLogin().rellenaFormulario(driver, "user1", "user1");

		SeleniumUtils.clickButton(driver, "botonesListas:theInbox");
		SeleniumUtils.esperaCargaPaginaxpath(driver, "/html/body/form[3]/div[2]", 5);

		elementos = SeleniumUtils.esperaCargaPaginaxpath(driver, "/html/body/form[3]", 6);
		assertTrue(elementos != null);

		SeleniumUtils.textoPresentePagina(driver, "tarea20");
		SeleniumUtils.rellenarTextFieldxpath(driver, "html/body/form[3]/div[2]/div[2]/table/thead/tr/th[1]/input", "tarea11");
		Thread.sleep(1000);
		SeleniumUtils.textoNoPresentePagina(driver, "tarea20");
		SeleniumUtils.textoPresentePagina(driver, "tarea11");
		

	}

	// PR19: Funcionamiento correcto de la ordenación por categoría tabla Hoy.
	@Test
	public void prueba19(){
		new PO_FormLogin().rellenaFormulario(driver, "user1", "user1");

		SeleniumUtils.clickButton(driver, "botonesListas:hoy");
		elementos = SeleniumUtils.esperaCargaPagina(driver, "id",
				"tablaDelUsuario:tablaTareas", 10);
		// Encontrar elemento de la siguiente vista

		assertTrue(elementos != null);

		SeleniumUtils.esperaCargaPaginaxpath(driver,"/html/body/"
				+ "form[3]/div[2]/div[2]/table/thead/tr/th[2]/span[2]", 4).get(0).click();
		SeleniumUtils.esperaCargaPaginaxpath(driver,"/html/body/"
				+ "form[3]/div[2]/div[2]/table/thead/tr/th[2]/span[2]", 4).get(0).click();

		String tarea = SeleniumUtils.esperaCargaPaginaxpath(driver, "/html/body/form[3]/div[2]/div[2]/table/tbody/tr[1]/td[1]/label/label", 4).get(0).getText();
		assertEquals("tarea20",tarea);
	}

	// PR20: Funcionamiento correcto de la ordenación por fecha planeada tabla Hoy.
	@Test
	public void prueba20() {
		new PO_FormLogin().rellenaFormulario(driver, "user1", "user1");
		
		SeleniumUtils.clickButton(driver, "botonesListas:hoy");
		elementos = SeleniumUtils.esperaCargaPagina(driver, "id",
				"tablaDelUsuario:tablaTareas", 10);
		

				assertTrue(elementos != null);

				SeleniumUtils.esperaCargaPaginaxpath(driver,
						"/html/body/form[3]/div[2]/div[2]/table/thead/tr/th[4]", 4).get(0).click();
				SeleniumUtils.esperaCargaPaginaxpath(driver,
						"/html/body/form[3]/div[2]/div[2]/table/thead/tr/th[4]", 4).get(0).click();
				String tarea = SeleniumUtils.esperaCargaPaginaxpath(driver, "/html/body/form[3]/div[2]/div[2]/table/tbody/tr[1]/td[1]/label/label",4).get(0).getText();
				assertEquals("tarea11",tarea);
	}

	// PR21: Comprobar que las tareas que no están en rojo son las de hoy y
	// además las que deben ser.
	@Test
	public void prueba21() throws ParseException {
		new PO_FormLogin().rellenaFormulario(driver, "user1", "user1");
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		// Encontrar elemento de la siguiente vista
		elementos = SeleniumUtils.esperaCargaPagina(driver, "id",
				"tablaDelUsuario", 6);
		assertTrue(elementos != null);
		SeleniumUtils.clickButton(driver, "botonesListas:hoy");
		SeleniumUtils.esperaCargaPagina(driver, "id",
				"tablaDelUsuario:tablaTareas", 10);
		for (int i = 0; i < 8; i++) {
			WebElement e = SeleniumUtils.esperaCargaPagina(driver, "id",
					"tablaDelUsuario:tablaTareas:" + i + ":planificada", 5)
					.get(0);
			if (format.parse(e.getText()).compareTo(new Date()) > 1) {
				assertEquals("rgba(79, 79,79 , 1)", e.getCssValue("color"));
			}
		}
		driver.findElement(By.className("ui-paginator-next")).click();
		for (int i = 8; i < 16; i++) {
			WebElement e = SeleniumUtils.esperaCargaPagina(driver, "id",
					"tablaDelUsuario:tablaTareas:" + i + ":planificada", 5)
					.get(0);
			if (format.parse(e.getText()).compareTo(new Date()) > 1) {
				assertEquals("rgba(79, 79,79, 1)", e.getCssValue("color"));
			}
		}
		driver.findElement(By.className("ui-paginator-next")).click();
		for (int i = 16; i < 20; i++) {
			WebElement e = SeleniumUtils.esperaCargaPagina(driver, "id",
					"tablaDelUsuario:tablaTareas:" + i + ":planificada", 5)
					.get(0);
			if (format.parse(e.getText()).compareTo(new Date()) > 1) {
				assertEquals("rgba(79, 79,79, 1)", e.getCssValue("color"));
			}
		}
	}

	// PR22: Comprobar que las tareas retrasadas están en rojo y son las que
	// deben ser.
	@Test
	public void prueba22() throws ParseException {
		new PO_FormLogin().rellenaFormulario(driver, "user1", "user1");
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		// Encontrar elemento de la siguiente vista
		elementos = SeleniumUtils.esperaCargaPagina(driver, "id",
				"tablaDelUsuario", 6);
		assertTrue(elementos != null);
		SeleniumUtils.clickButton(driver, "botonesListas:hoy");
		SeleniumUtils.esperaCargaPagina(driver, "id",
				"tablaDelUsuario:tablaTareas", 10);
		for (int i = 0; i < 8; i++) {
			WebElement e = SeleniumUtils.esperaCargaPagina(driver, "id",
					"tablaDelUsuario:tablaTareas:" + i + ":planificada", 5)
					.get(0);
			if (format.parse(e.getText()).compareTo(new Date()) < 1) {
				assertEquals("rgba(255, 0, 0, 1)", e.getCssValue("color"));
			}
		}
		driver.findElement(By.className("ui-paginator-next")).click();
		for (int i = 8; i < 16; i++) {
			WebElement e = SeleniumUtils.esperaCargaPagina(driver, "id",
					"tablaDelUsuario:tablaTareas:" + i + ":planificada", 5)
					.get(0);
			if (format.parse(e.getText()).compareTo(new Date()) < 1) {
				assertEquals("rgba(255, 0, 0, 1)", e.getCssValue("color"));
			}
		}
		driver.findElement(By.className("ui-paginator-next")).click();
		for (int i = 16; i < 20; i++) {
			WebElement e = SeleniumUtils.esperaCargaPagina(driver, "id",
					"tablaDelUsuario:tablaTareas:" + i + ":planificada", 5)
					.get(0);
			if (format.parse(e.getText()).compareTo(new Date()) < 1) {
				assertEquals("rgba(255, 0, 0, 1)", e.getCssValue("color"));
			}
		}
	}

	// PR23: Comprobar que las tareas de hoy y futuras no están en rojo y que
	// son las que deben ser.
	@Test
	public void prueba23() throws ParseException {
		new PO_FormLogin().rellenaFormulario(driver, "user1", "user1");
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		// Encontrar elemento de la siguiente vista
		elementos = SeleniumUtils.esperaCargaPagina(driver, "id",
				"tablaDelUsuario", 6);
		assertTrue(elementos != null);
		SeleniumUtils.clickButton(driver, "botonesListas:semana");
		SeleniumUtils.esperaCargaPagina(driver, "id",
				"tablaDelUsuario:tablaTareas", 10);
		for (int i = 0; i < 8; i++) {
			WebElement e = SeleniumUtils.esperaCargaPagina(driver, "id",
					"tablaDelUsuario:tablaTareas:" + i + ":planificada", 5)
					.get(0);
			if (format.parse(e.getText()).compareTo(new Date()) > 1) {
				assertEquals("rgba(79, 79,79 , 1)", e.getCssValue("color"));
			}
		}
		driver.findElement(By.className("ui-paginator-next")).click();
		for (int i = 8; i < 16; i++) {
			WebElement e = SeleniumUtils.esperaCargaPagina(driver, "id",
					"tablaDelUsuario:tablaTareas:" + i + ":planificada", 5)
					.get(0);
			if (format.parse(e.getText()).compareTo(new Date()) > 1) {
				assertEquals("rgba(79, 79,79, 1)", e.getCssValue("color"));
			}
		}
		driver.findElement(By.className("ui-paginator-next")).click();
		for (int i = 16; i < 24; i++) {
			WebElement e = SeleniumUtils.esperaCargaPagina(driver, "id",
					"tablaDelUsuario:tablaTareas:" + i + ":planificada", 5)
					.get(0);
			if (format.parse(e.getText()).compareTo(new Date()) > 1) {
				assertEquals("rgba(79, 79,79, 1)", e.getCssValue("color"));
			}
		}
		driver.findElement(By.className("ui-paginator-next")).click();
		for (int i = 24; i < 30; i++) {
			WebElement e = SeleniumUtils.esperaCargaPagina(driver, "id",
					"tablaDelUsuario:tablaTareas:" + i + ":planificada", 5)
					.get(0);
			if (format.parse(e.getText()).compareTo(new Date()) > 1) {
				assertEquals("rgba(79, 79,79, 1)", e.getCssValue("color"));
			}
		}
	}

	// PR24: Funcionamiento correcto de la ordenación por día.
	@Test
	public void prueba24() {
		new PO_FormLogin().rellenaFormulario(driver, "user1", "user1");

		SeleniumUtils.clickButton(driver, "botonesListas:semana");
		elementos = SeleniumUtils.esperaCargaPagina(driver, "id",
				"tablaDelUsuario:tablaTareas", 10);
		// Encontrar elemento de la siguiente vista

		assertTrue(elementos != null);

		SeleniumUtils
				.esperaCargaPaginaxpath(
						driver,
						"/html/body/form[3]/div[2]/div[2]/table/thead/tr/th[4]",
						4).get(0).click();
		SeleniumUtils
				.esperaCargaPaginaxpath(
						driver,
						"/html/body/form[3]/div[2]/div[2]/table/thead/tr/th[4]",
						4).get(0).click();

		String tarea = SeleniumUtils
				.esperaCargaPaginaxpath(
						driver,
						"/html/body/form[3]/div[2]/div[2]/table/tbody/tr[1]/td[1]/label",
						4).get(0).getText();
		assertEquals("tarea1", tarea);
	}

	// PR25: Funcionamiento correcto de la ordenación por nombre.
	@Test
	public void prueba25() {
		new PO_FormLogin().rellenaFormulario(driver, "user1", "user1");

		SeleniumUtils.clickButton(driver, "botonesListas:semana");
		elementos = SeleniumUtils.esperaCargaPagina(driver, "id",
				"tablaDelUsuario:tablaTareas", 10);
		// Encontrar elemento de la siguiente vista

		assertTrue(elementos != null);

		SeleniumUtils
				.esperaCargaPaginaxpath(
						driver,
						"/html/body/form[3]/div[2]/div[2]/table/thead/tr/th[1]/span[2]",
						4).get(0).click();
		SeleniumUtils
				.esperaCargaPaginaxpath(
						driver,
						"/html/body/form[3]/div[2]/div[2]/table/thead/tr/th[1]/span[2]",
						4).get(0).click();

		String tarea = SeleniumUtils
				.esperaCargaPaginaxpath(
						driver,
						"/html/body/form[3]/div[2]/div[2]/table/tbody/tr[1]/td[1]/label",
						4).get(0).getText();
		assertEquals("tarea9", tarea);
	}

	// PR26: Confirmar una tarea, inhabilitar el filtro de tareas terminadas, ir
	// a la pagina donde está la tarea terminada y comprobar que se muestra.
	@Test
	public void prueba26() {
		// Nos logueamos
		new PO_FormLogin().rellenaFormulario(driver, "user1", "user1");
		
		//Esperamos a la tabla de tareas
		SeleniumUtils.esperaCargaPagina(driver, "id", "tablaDelUsuario:tablaTareas", 10);
		//Finalizamos la tarea21
		SeleniumUtils.textoPresentePagina(driver, "tarea21");
		SeleniumUtils.clickButton(driver, "tablaDelUsuario:tablaTareas:0:finalizar");
		SeleniumUtils.esperaCargaPagina(driver, "id", "tablaDelUsuario:tablaTareas", 10);
		SeleniumUtils.textoNoPresentePagina(driver, "tarea21");
		
		//Vamos a Inbox, última página. Allí estará la tarea finalizada.
		SeleniumUtils.clickButton(driver, "botonesListas:theInbox");
		SeleniumUtils.esperaCargaPagina(driver, "id", "tablaDelUsuario:tablaTareas", 10);
		SeleniumUtils.textoPresentePagina(driver, "Inbox terminadas");
		SeleniumUtils.seleccionarPagina(driver, "ui-paginator-page "
				+ "ui-state-default ui-corner-all", 1);
		SeleniumUtils.esperaCargaPagina(driver, "id", "tablaDelUsuario:tablaTareas", 10);
		SeleniumUtils.textoPresentePagina(driver, "tarea21");
		
		WebElement e = SeleniumUtils.esperaCargaPaginaxpath(driver, "/html/body"
				+ "/form[3]/div[2]/div[2]/table/tbody/tr[5]/td[1]/label/label", 5)
				.get(0); 
		assertEquals("rgba(76, 175, 80, 1)", e.getCssValue("color"));
		
	}

	// PR27: Crear una tarea sin categoría y comprobar que se muestra en la
	// lista Inbox.
	@Test
	public void prueba27() { 
		// Nos logueamos
		new PO_FormLogin().rellenaFormulario(driver, "user1", "user1");

		// Hacemos clic en crear tarea
		SeleniumUtils
				.esperaCargaPagina(driver, "id", "menu1:gestionSesion", 10);
		SeleniumUtils.clickSubopcionMenuHover(driver, "menu1:gestionTareas",
				"menu1:tareas");

		// Rellenamos el formulario
		new PO_FormCrearTarea().rellenaFormulario(driver, "NuevaTareaSinCategoria",
				"Tarea sin categoria", "31/05/2017  08:28:35",
				"Category_1");

		// Nos debería redirigir a Inbox
		SeleniumUtils
				.esperaCargaPaginaxpath(driver, "/html/body/form[3]/div[2]", 5);
		SeleniumUtils.textoPresentePagina(driver, "Inbox no terminadas");

		SeleniumUtils.seleccionarPagina(driver, "ui-paginator-page "
				+ "ui-state-default ui-corner-all", 1);

		// En la última página debería estar dicha tarea
		SeleniumUtils
				.esperaCargaPagina(driver, "id", "menu1:gestionSesion", 10);
		String s = SeleniumUtils.esperaCargaPaginaxpath(driver,"/html/body/form[3]/div[2]/div[2]/table/tbody/tr[5]/td[1]/label/label", 4).get(0).getText();
		assertEquals("NuevaTareaSinCategoria",s);

	}

	// PR28: Crear una tarea con categoría categoria1 y fecha planeada Hoy y
	// comprobar que se muestra en la lista Hoy.
	@Test
	public void prueba28() {
		// Nos logueamos
		new PO_FormLogin().rellenaFormulario(driver, "user1", "user1");

		// Hacemos clic en crear tarea
		SeleniumUtils
				.esperaCargaPagina(driver, "id", "menu1:gestionSesion", 10);
		SeleniumUtils.clickSubopcionMenuHover(driver, "menu1:gestionTareas",
				"menu1:tareas");

		DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
		String hoy = dateFormat.format(new Date()) + "  23:59:50";

		// Rellenamos el formulario
		new PO_FormCrearTarea().rellenaFormulario(driver,
				"NuevaTareaCategoria1Hoy", "Tarea con categoria 1 para hoy",
				hoy, "Category_2");

		// Nos debería redirigir a Hoy
		SeleniumUtils
				.esperaCargaPagina(driver, "id", "tablaDelUsuario:tablaTareas", 10);
		SeleniumUtils.textoPresentePagina(driver, "Lista de hoy");

		SeleniumUtils
				.esperaCargaPagina(driver, "id", "menu1:gestionSesion", 10);
		String s = SeleniumUtils.esperaCargaPaginaxpath(driver,"/html/body/form[3]/div[2]/div[2]/table/tbody/tr[3]/td[1]/label/label", 4).get(0).getText();
		assertEquals("NuevaTareaCategoria1Hoy",s);
	}

	// PR29: Crear una tarea con categoría categoria1 y fecha planeada posterior
	// a Hoy y comprobar que se muestra en la lista Semana.
	@Test
	public void prueba29() {
		// Nos logueamos
		new PO_FormLogin().rellenaFormulario(driver, "user1", "user1");

		// Hacemos clic en crear tarea
		SeleniumUtils
				.esperaCargaPagina(driver, "id", "menu1:gestionSesion", 10);
		SeleniumUtils.clickSubopcionMenuHover(driver, "menu1:gestionTareas",
				"menu1:tareas");

		DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
		String hoy = dateFormat.format(new Date(
				System.currentTimeMillis() + 500000000)) + "  00:00:00";

		// Rellenamos el formulario
		new PO_FormCrearTarea().rellenaFormulario(driver,
				"NuevaTareaCategoria1Semana",
				"Tarea con categoria 1 para semana", hoy,
				"Category_2");

		// Nos debería redirigir a Hoy
		SeleniumUtils
				.esperaCargaPagina(driver, "id", "tablaDelUsuario:tablaTareas", 10);
		SeleniumUtils.textoPresentePagina(driver, "Lista esta semana");

		SeleniumUtils.seleccionarPagina(driver, "ui-paginator-page ", 1);

		// En la última página debería estar dicha tarea
		SeleniumUtils
				.esperaCargaPagina(driver, "id", "menu1:gestionSesion", 10);
		String s = SeleniumUtils.esperaCargaPaginaxpath(driver, "/html/body/form[3]/div[2]/div[2]/table/tbody/tr[3]/td[1]/label/label", 4).get(0).getText();
		assertEquals( "NuevaTareaCategoria1Semana",s);
	}

	// PR30: Editar el nombre, y categoría de una tarea (se le cambia a
	// categoría1) de la lista Inbox y comprobar que las tres pseudolista se
	// refresca correctamente.
	@Test
	public void prueba30() {
		// Nos logueamos
		new PO_FormLogin().rellenaFormulario(driver, "user1", "user1");

		// Click en las tareas de inbox
		SeleniumUtils.esperaCargaPagina(driver, "id", "botonesListas:theInbox", 10);
		SeleniumUtils.clickButton(driver, "botonesListas:theInbox");

		// Click en editar tarea20 (tarea sin categoria de inbox).
		SeleniumUtils.esperaCargaPagina(driver, "id",
				"tablaDelUsuario:tablaTareas:0:editar", 10);
		SeleniumUtils.clickButton(driver, "tablaDelUsuario:tablaTareas:0:editar");

		// Rellenamos el formulario para editar la tarea20
		new PO_FormEditarTarea().rellenaFormulario(driver, "tarea1Modificada", null,
				null, "Categoria1");

		/*
		 * Al tener fecha para hoy y categoria: -No aparece en Inbox (Porque
		 * tiene categoría) -No aparece en Hoy (porque no es para hoy) -Aparece
		 * en semana (porqu tiene fecha para esta semana)
		 */

		// Esperamos que aparezcan los enlaces de paginacion y hacemos click
		SeleniumUtils.clickButton(driver, "botonesListas:semana");

		SeleniumUtils.seleccionarPagina(driver, "ui-paginator-page", 2);
		//SeleniumUtils.textoPresentePagina(driver, "tarea1Modificada");
		WebElement elemento = SeleniumUtils.esperaCargaPagina(driver, "id", "tablaDelUsuario:tablaTareas:10:title" , 4).get(0);
		
		assertEquals("tarea1Modificada",elemento.getText());

	}

	// PR31: Editar el nombre, y categoría (Se cambia a sin categoría) de una
	// tarea de la lista Hoy y comprobar que las tres pseudolistas se refrescan
	// correctamente.
	@Test
	public void prueba31() {
		// Nos logueamos
		new PO_FormLogin().rellenaFormulario(driver, "user1", "user1");

		// Click en las tareas de hoy
		SeleniumUtils.esperaCargaPagina(driver, "id", "botonesListas:hoy", 10);
		SeleniumUtils.clickButton(driver, "botonesListas:hoy");

		// Esperamos que aparezcan los enlaces de paginacion y hacemos click
		List<WebElement> paginacion = SeleniumUtils.esperaCargaPagina(driver,
				"class", "ui-paginator-pages", 2);
		SeleniumUtils.clickElement(driver, paginacion.get(1));

		// Click en editar tarea30 (tarea con categoria 3).
		SeleniumUtils.esperaCargaPaginaxpath(driver,
				"/html/body/form[3]/div[2]/div[2]/table/tbody/tr[2]/td[7]/button", 5);
		driver.findElement(By.xpath("/html/body/form[3]/div[2]/div[2]/table/tbody/tr[2]/td[7]/button")).click();
		
		SeleniumUtils.esperaCargaPaginaxpath(driver, 
				"/html/body/form[2]/table/tbody/tr[1]/td[2]/input", 5);

		// Rellenamos el formulario para editar tarea
		new PO_FormCrearTarea().rellenaFormulario(driver, "tarea30Modificada",
				null, null, "Sin categoria");

		/*
		 * Al tener fecha para hoy y categoria: -No aparece en Inbox (Porque
		 * tiene categoría) -Aparece en Hoy (porque es para hoy) -Aparece en
		 * semana (porque es también para esta semana)
		 */

	}

	// PR32: Marcar una tarea como finalizada. Comprobar que desaparece de las
	// tres pseudolistas.
	@Test
	public void prueba32() {
		// Nos logueamos
		new PO_FormLogin().rellenaFormulario(driver, "user1", "user1");

		// Click en Finalizar tarea21
		elementos = SeleniumUtils.esperaCargaPagina(driver, "id",
				"tablaDelUsuario:tablaTareas:0:finalizar", 10);
		SeleniumUtils.textoPresentePagina(driver, "tarea21");
		elementos.get(0).click();
		//SeleniumUtils.clickButton(driver, "tablaDelUsuario:tablaTareas:0:finalizar");
		SeleniumUtils
				.esperaCargaPagina(driver, "id", "tablaDelUsuario:tablaTareas", 10);
		SeleniumUtils.textoNoPresentePagina(driver, "tarea21");

		// Vemos que no está en ninguna página
		SeleniumUtils.seleccionarPagina(driver, "ui-paginator-page "
				+ "ui-state-default ui-corner-all", 0);
		SeleniumUtils
				.esperaCargaPagina(driver, "id", "tablaDelUsuario:tablaTareas", 10);
		SeleniumUtils.textoNoPresentePagina(driver, "tarea21");

		SeleniumUtils.seleccionarPagina(driver, "ui-paginator-page "
				+ "ui-state-default ui-corner-all", 2);
		SeleniumUtils
				.esperaCargaPagina(driver, "id", "tablaDelUsuario:tablaTareas", 10);
		SeleniumUtils.textoNoPresentePagina(driver, "tarea21");

		SeleniumUtils.seleccionarPagina(driver, "ui-paginator-page "
				+ "ui-state-default ui-corner-all", 3);
		SeleniumUtils
				.esperaCargaPagina(driver, "id", "tablaDelUsuario:tablaTareas", 10);
		SeleniumUtils.textoNoPresentePagina(driver, "tarea21");

		// Lista de hoy
		SeleniumUtils.clickButton(driver, "botonesListas:hoy");
		SeleniumUtils
				.esperaCargaPagina(driver, "id", "tablaDelUsuario:tablaTareas", 10);
		SeleniumUtils.textoNoPresentePagina(driver, "tarea21");

		SeleniumUtils.seleccionarPagina(driver, "ui-paginator-page", 1);
		SeleniumUtils
				.esperaCargaPagina(driver, "id", "tablaDelUsuario:tablaTareas", 10);
		SeleniumUtils.textoNoPresentePagina(driver, "tarea21");

		SeleniumUtils.seleccionarPagina(driver, "ui-paginator-page", 2);
		SeleniumUtils
				.esperaCargaPagina(driver, "id", "tablaDelUsuario:tablaTareas", 10);
		SeleniumUtils.textoNoPresentePagina(driver, "tarea21");
		
		SeleniumUtils.seleccionarPagina(driver, "ui-paginator-page", 3);
		SeleniumUtils
				.esperaCargaPagina(driver, "id", "tablaDelUsuario:tablaTareas", 10);
		SeleniumUtils.textoNoPresentePagina(driver, "tarea21");

		// Lista de semana
		SeleniumUtils.clickButton(driver, "botonesListas:semana");
		SeleniumUtils
				.esperaCargaPagina(driver, "id", "tablaDelUsuario:tablaTareas", 10);
		SeleniumUtils.seleccionarPagina(driver, "ui-paginator-page ", 1);
		SeleniumUtils
				.esperaCargaPagina(driver, "id", "tablaDelUsuario:tablaTareas", 10);
		SeleniumUtils.textoNoPresentePagina(driver, "tarea21");

		SeleniumUtils.seleccionarPagina(driver, "ui-paginator-page", 2);
		SeleniumUtils
				.esperaCargaPagina(driver, "id", "tablaDelUsuario:tablaTareas", 10);
		SeleniumUtils.textoNoPresentePagina(driver, "tarea21");

		SeleniumUtils.seleccionarPagina(driver, "ui-paginator-page "
				+ "ui-state-default ui-corner-all", 2);
		SeleniumUtils
				.esperaCargaPagina(driver, "id", "tablaDelUsuario:tablaTareas", 10);
		SeleniumUtils.textoNoPresentePagina(driver, "tarea21");
		
		SeleniumUtils.seleccionarPagina(driver, "ui-paginator-page "
				+ "ui-state-default ui-corner-all", 3);
		SeleniumUtils
				.esperaCargaPagina(driver, "id", "tablaDelUsuario:tablaTareas", 10);
		SeleniumUtils.textoNoPresentePagina(driver, "tarea21");


		// Lista de Inbox
		// Aqui debería estar la tarea finalizada al final de la lista
		SeleniumUtils.clickButton(driver, "botonesListas:theInbox");
		SeleniumUtils
				.esperaCargaPagina(driver, "id", "tablaDelUsuario:tablaTareas", 10);
		SeleniumUtils.seleccionarPagina(driver, "ui-paginator-page "
				+ "ui-state-default ui-corner-all", 0);
		SeleniumUtils
				.esperaCargaPagina(driver, "id", "tablaDelUsuario:tablaTareas", 10);
		SeleniumUtils.textoNoPresentePagina(driver, "tarea21");

		SeleniumUtils.seleccionarPagina(driver, "ui-paginator-page "
				+ "ui-state-default ui-corner-all", 1);
		SeleniumUtils
				.esperaCargaPagina(driver, "id", "tablaDelUsuario:tablaTareas", 10);
		SeleniumUtils.textoNoPresentePagina(driver, "tarea21");

		// Última página, aqui debería estar
		SeleniumUtils.seleccionarPagina(driver, "ui-paginator-page "
				+ "ui-state-default ui-corner-all", 2);
		SeleniumUtils
				.esperaCargaPagina(driver, "id", "tablaDelUsuario:tablaTareas", 10);
		SeleniumUtils.textoPresentePagina(driver, "tarea21");

		WebElement e = SeleniumUtils
				.esperaCargaPaginaxpath(
						driver,
						"/html/body/form[3]/div[2]/div[2]/table/tbody/tr[5]/td[1]/label/label",
						4).get(0);
		assertEquals("rgba(76, 175, 80, 1)", e.getCssValue("color"));
	}

	// PR33: Salir de sesión desde cuenta de administrador.
	@Test
	public void prueba33() {
		// Nos logueamos
		new PO_FormLogin().rellenaFormulario(driver, "admin1", "admin1");
		// cerramos sesion
		SeleniumUtils
				.esperaCargaPagina(driver, "id", "menu1:gestionSesion", 10);
		SeleniumUtils.clickSubopcionMenuHover(driver, "menu1:gestionSesion",
				"menu1:cerrarSesion");

		// Comprobamos que estamos en index.xhtml
		SeleniumUtils.esperaCargaPagina(driver, "id", "menu1:options", 10);
		SeleniumUtils.textoPresentePagina(driver, "Inicio de sesión");
		SeleniumUtils.textoNoPresentePagina(driver, "Lista de usuarios");
		SeleniumUtils.textoNoPresentePagina(driver, "Lista de inbox");
	}

	// PR34: Salir de sesión desde cuenta de usuario normal.
	@Test
	public void prueba34() {
		// Nos logueamos
		new PO_FormLogin().rellenaFormulario(driver, "user1", "user1");
		// cerramos sesion
		SeleniumUtils
				.esperaCargaPagina(driver, "id", "menu1:gestionSesion", 10);
		SeleniumUtils.clickSubopcionMenuHover(driver, "menu1:gestionSesion",
				"menu1:cerrarSesion");

		// Comprobamos que estamos en index.xhtml
		SeleniumUtils.esperaCargaPagina(driver, "id", "menu1:options", 10);
		SeleniumUtils.textoPresentePagina(driver, "Inicio de sesión");
		SeleniumUtils.textoNoPresentePagina(driver, "Lista de usuarios");
		SeleniumUtils.textoNoPresentePagina(driver, "Lista de inbox");
	}

	// PR35: Cambio del idioma por defecto a un segundo idioma. (Probar algunas
	// vistas)
	@Test
	public void prueba35() {
		 SeleniumUtils.esperaCargaPagina(driver, "id", "menu1", 5);
		 SeleniumUtils.textoPresentePagina(driver, "Inicio de sesión");
		 SeleniumUtils.clickSubopcionMenuHover(driver, "menu1:options",
		 "menu1:optionEnglish");
		 SeleniumUtils.esperaCargaPagina(driver, "text", "Language", 4);
		 //SeleniumUtils.textoPresentePagina(driver, "Login");
		
		 new PO_FormLogin().rellenaFormulario(driver, "user1", "user1");
		 SeleniumUtils.esperaCargaPagina(driver, "id",
		 "tablaDelUsuario:tablaTareas", 5);
		 SeleniumUtils.textoPresentePagina(driver, "Login");
		
		 SeleniumUtils.clickButton(driver, "botonesListas:hoy");
		 SeleniumUtils.esperaCargaPagina(driver, "id",
		 "tablaDelUsuario:tablaTareas", 10);
		 SeleniumUtils.textoPresentePagina(driver, "Today list");
		 SeleniumUtils.clickSubopcionMenuHover(driver, "menu1:gestionSesion",
		 "menu1:cerrarSesion");
		
		 new PO_FormLogin().rellenaFormulario(driver, "admin1", "admin1");
		 SeleniumUtils.textoPresentePagina(driver, "Administrator");
		
	}

	// PR36: Cambio del idioma por defecto a un segundo idioma y vuelta al
	// idioma por defecto. (Probar algunas vistas)
	@Test
	public void prueba36() {
		// TODO Por hacer;
		assertTrue(false);
	}

	// PR37: Intento de acceso a un URL privado de administrador con un usuario
	// autenticado como usuario normal.
	@Test
	public void prueba37() {
		// Nos logueamos
		new PO_FormLogin().rellenaFormulario(driver, "user1", "user1");
		// Esperamos a que se cargue la pagina de listado concretamente
		// la tabla "j_idt15:tablaTareas"
		SeleniumUtils
				.esperaCargaPagina(driver, "id", "menu1:gestionSesion", 10);
		// Vemos que estamos en usuario.xhtml
		SeleniumUtils.textoPresentePagina(driver, "Lista de inbox");
		// Vamos hacia una URL de usuario con rol administrador
		driver.get(localhost() + "/sdi2-56/restricted/admin/"
				+ "administrador.xhtml");

		/*
		 * Aquí es donde el filtro nos debería devolver a otra página distinta a
		 * administrador.xhtml, ya que el usuario normal no tiene permisos para
		 * entrar en una página de usuario administrador. Hemos configurado el
		 * filtro para que cuando esto ocurra nos devuelva a usuario.xhtml.
		 */

		// Comprobamos que seguimos en administrador.xhtml viendo el texto.
		SeleniumUtils.esperaCargaPagina(driver, "id", "menu1:options", 10);
		SeleniumUtils.textoNoPresentePagina(driver, "Inicio de sesión");
		SeleniumUtils.textoNoPresentePagina(driver, "Lista de usuarios");
		SeleniumUtils.textoPresentePagina(driver, "Lista de inbox");
		// Si el texto existe estamos en administrador.xhtml, prueba completa
	}

	// PR38: Intento de acceso a un URL privado de usuario normal con un usuario
	// no autenticado.
	@Test
	public void prueba38() {
		// Esperamos a que se cargue la pagina de listado concretamente
		// la tabla "loginForm:user"
		SeleniumUtils.esperaCargaPagina(driver, "id", "loginForm:user", 10);
		// Vamos hacia una URL de usuario con rol usuario
		driver.get(localhost() + "/sdi2-56/restricted/user/usuario.xhtml");

		/*
		 * Aquí es donde el filtro nos debería devolver a otra página distinta a
		 * usuario.xhtml, ya que el usuario anonimo no tiene permisos para
		 * entrar. Hemos configurado el filtro para que cuando esto ocurra nos
		 * devuelva a index.xhtml.
		 */

		// Comprobamos que seguimos en index.xhtml viendo un texto.
		SeleniumUtils.esperaCargaPagina(driver, "id", "menu1:options", 10);
		SeleniumUtils.textoPresentePagina(driver, "Inicio de sesión");
		SeleniumUtils.textoNoPresentePagina(driver, "Lista de usuarios");
		SeleniumUtils.textoNoPresentePagina(driver, "Lista de inbox");
		// Si el texto existe, prueba completa
	}

}