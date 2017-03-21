package com.sdi.tests.Tests;

import static org.junit.Assert.*;

import java.io.File;
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
import org.openqa.selenium.interactions.Actions;

import com.sdi.tests.pageobjects.PO_FormLogin;
import com.sdi.tests.utils.SeleniumUtils;

//Ordenamos las pruebas por el nombre del método
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class PlantillaSDI2_Tests1617 {

	WebDriver driver;
	List<WebElement> elementos = null;
	boolean isInterno = true;

	private String localhost() {
		if (isInterno) {
			return "http://localhost:8280";
		}
		return "http://localhost:8180";
	}
	
	private void reiniciarBBDD(){
		// Nos logueamos como administrador
		new PO_FormLogin().rellenaFormulario(driver, "admin1", "admin1");
		// Reiniciamos la BBDD
		SeleniumUtils.clickSubopcionMenuHover(driver, "menu1:BBDD", 
				"menu1:ReiniciarBBDD");
		//Cerramos sesion con el admin
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
		//Siempre iniciamos las pruebas con una base de datos limpia
		reiniciarBBDD();
		// Este código es para ejecutar con una versión instalada de Firex 46.0
		// driver = new FirefoxDriver();
		// driver.get("http://localhost:8180/sdi2-n");
	}

	@After
	public void end() {
		// Cerramos el navegador
		//TODO Si se quieren cerrar todas las ventanas una vez terminen: 
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
		new PO_FormLogin().rellenaFormulario(driver, "administrador?", "admin1");

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
		// TODO Por hacer;
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

		SeleniumUtils.clickButton(driver, "form-listado:tabla:0:j_idt19");

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

		SeleniumUtils.clickButton(driver, "form-listado:tabla:0:j_idt19");

		SeleniumUtils.clickSubopcionMenuHover(driver, "menu1:gestionSesion",
				"menu1:cerrarSesion");
		
		//Nos logueamos como user1
		login.rellenaFormulario(driver, "user1", "user1");

		SeleniumUtils.esperaCargaPagina(driver, "id", "loginForm:user", 10);

		SeleniumUtils.textoPresentePagina(driver, "mejor suerte en otra vida");

		// Nos logueamos como admin
		login.rellenaFormulario(driver, "admin1", "admin1");
		
		// Encontrar elemento de la siguiente vista
		elementos = SeleniumUtils.esperaCargaPagina(driver, "id",
				"form-listado", 6);
		assertTrue(elementos != null);

		SeleniumUtils.clickButton(driver, "form-listado:tabla:0:j_idt19");

		SeleniumUtils.clickSubopcionMenuHover(driver, "menu1:gestionSesion",
				"menu1:cerrarSesion");

		// Nos logueamos como user1
		login.rellenaFormulario(driver, "user1", "user1");

		elementos = SeleniumUtils.esperaCargaPagina(driver, "id",
				"j_idt13:tablaTareas", 6);
		assertTrue(elementos != null);
	}

	// PR08: Ordenar por Login
	@Test
	public void prueba08() {
		// TODO Por hacer;
	}

	// PR09: Ordenar por Email
	@Test
	public void prueba09() {
		// TODO Por hacer;
	}

	// PR10: Ordenar por Status
	@Test
	public void prueba10() {
		// TODO Por hacer;
	}

	// PR11: Borrar una cuenta de usuario normal y datos relacionados.
	@Test
	public void prueba11() {
		// TODO Por hacer;
	}

	// PR12: Crear una cuenta de usuario normal con datos válidos.
	@Test
	public void prueba12() {
		// TODO Por hacer;
	}

	// PR13: Crear una cuenta de usuario normal con login repetido.
	@Test
	public void prueba13() {
		// TODO Por hacer;
	}

	// PR14: Crear una cuenta de usuario normal con Email incorrecto.
	@Test
	public void prueba14() {
		// TODO Por hacer;
	}

	// PR15: Crear una cuenta de usuario normal con Password incorrecta.
	@Test
	public void prueba15() {
		// TODO Por hacer;
	}

	// USUARIO
	// PR16: Comprobar que en Inbox sólo aparecen listadas las tareas sin
	// categoría y que son las que tienen que. Usar paginación navegando por las
	// tres páginas.
	@Test
	public void prueba16() {
		// TODO Por hacer;
	}

	// PR17: Funcionamiento correcto de la ordenación por fecha planeada.
	@Test
	public void prueba17() {
		// TODO Por hacer;
	}

	// PR18: Funcionamiento correcto del filtrado.
	@Test
	public void prueba18() {
		// TODO Por hacer;
	}

	// PR19: Funcionamiento correcto de la ordenación por categoría.
	@Test
	public void prueba19() {
		// TODO Por hacer;
	}

	// PR20: Funcionamiento correcto de la ordenación por fecha planeada.
	@Test
	public void prueba20() {
		// TODO Por hacer;
	}

	// PR21: Comprobar que las tareas que no están en rojo son las de hoy y
	// además las que deben ser.
	@Test
	public void prueba21() {
		// TODO Por hacer;
	}

	// PR22: Comprobar que las tareas retrasadas están en rojo y son las que
	// deben ser.
	@Test
	public void prueba22() {
		// TODO Por hacer;
	}

	// PR23: Comprobar que las tareas de hoy y futuras no están en rojo y que
	// son las que deben ser.
	@Test
	public void prueba23() {
		// TODO Por hacer;
	}

	// PR24: Funcionamiento correcto de la ordenación por día.
	@Test
	public void prueba24() {
		// TODO Por hacer;
	}

	// PR25: Funcionamiento correcto de la ordenación por nombre.
	@Test
	public void prueba25() {
		// TODO Por hacer;
	}

	// PR26: Confirmar una tarea, inhabilitar el filtro de tareas terminadas, ir
	// a la pagina donde está la tarea terminada y comprobar que se muestra.
	@Test
	public void prueba26() {
		// TODO Por hacer;
	}

	// PR27: Crear una tarea sin categoría y comprobar que se muestra en la
	// lista Inbox.
	@Test
	public void prueba27() {
		// TODO Por hacer;
	}

	// PR28: Crear una tarea con categoría categoria1 y fecha planeada Hoy y
	// comprobar que se muestra en la lista Hoy.
	@Test
	public void prueba28() {
		// TODO Por hacer;
	}

	// PR29: Crear una tarea con categoría categoria1 y fecha planeada posterior
	// a Hoy y comprobar que se muestra en la lista Semana.
	@Test
	public void prueba29() {
		// TODO Por hacer;
	}

	// PR30: Editar el nombre, y categoría de una tarea (se le cambia a
	// categoría1) de la lista Inbox y comprobar que las tres pseudolista se
	// refresca correctamente.
	@Test
	public void prueba30() {
		// TODO Por hacer;
	}

	// PR31: Editar el nombre, y categoría (Se cambia a sin categoría) de una
	// tarea de la lista Hoy y comprobar que las tres pseudolistas se refrescan
	// correctamente.
	@Test
	public void prueba31() {
		// TODO Por hacer;
	}

	// PR32: Marcar una tarea como finalizada. Comprobar que desaparece de las
	// tres pseudolistas.
	@Test
	public void prueba32() {
		// TODO Por hacer;
		new PO_FormLogin().rellenaFormulario(driver, "user1", "user1");

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
		// TODO Por hacer;
	}

	// PR36: Cambio del idioma por defecto a un segundo idioma y vuelta al
	// idioma por defecto. (Probar algunas vistas)
	@Test
	public void prueba36() {
		// TODO Por hacer;
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
		// Accedemos al index
		driver.get(localhost() + "/sdi2-56/");
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