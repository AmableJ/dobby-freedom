package uo.sdi.presentation;

import java.awt.event.ActionEvent;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;

import uo.sdi.business.util.BusinessException;
import uo.sdi.dto.CategoryDTO;
import uo.sdi.dto.TaskDTO;
import uo.sdi.dto.UserDTO;
import uo.sdi.infrastructure.Factories;

@ManagedBean(name = "tareas")
@SessionScoped
public class TaskBean {
	
	private List<TaskDTO> listaTareas = new ArrayList<TaskDTO>();
	//tarea sin valores que servira para poder trabajar con la tarea obtenida
	//de un formulario
	private TaskDTO tarea = new TaskDTO();
	
	public TaskBean(){
		iniciaTask(null);
	}
	
	public void iniciaTask(ActionEvent event) {
		tarea.setCategoryId(null);
		tarea.setTitle("");
		tarea.setUserId(null);
	    tarea.setComments("");
	    tarea.setPlanned(new Date());
	    tarea.setFinished(new Date());
	  }

	public void listar(String login) {
		try {
			listaTareas = Factories.services.createTaskService()
					.listUserTasks(login);
		} catch (BusinessException e) {
			e.printStackTrace();
		}
	}

	public void listarTaskInbox(UserDTO user) {
		try {
			listaTareas = Factories.services.createTaskService()
					.listTasksInbox(user.getLogin());
		} catch (BusinessException e) {
			e.printStackTrace();
		}
	}
	
	public void listarTaskHoy(UserDTO user) {
		try {
			listaTareas = Factories.services.createTaskService()
					.listTasksToday(user.getLogin());
		} catch (BusinessException e) {
			e.printStackTrace();
		}
	}
	
	public void listarTasksSemana(UserDTO user) {
		try {
			listaTareas = Factories.services.createTaskService()
					.listTasksWeek(user.getLogin());
		} catch (BusinessException e) {
			e.printStackTrace();
		}
	}
	
	public void listarByCategory(UserDTO user,CategoryDTO category) {
		try {
			listaTareas = Factories.services.createTaskService()
					.listTasksByCategory(user.getLogin(), category.getName());
		} catch (BusinessException e) {
			e.printStackTrace();
		}
	}

	public TaskDTO getTarea() {
		return tarea;
	}

	public void setTarea(TaskDTO tarea) {
		this.tarea = tarea;
	}

	public List<TaskDTO> getListaTareas() {
		return listaTareas;
	}

	public void setListaTareas(List<TaskDTO> listaTareas) {
		this.listaTareas = listaTareas;
	}
}
