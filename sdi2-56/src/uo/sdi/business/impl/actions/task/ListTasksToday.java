package uo.sdi.business.impl.actions.task;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import alb.util.date.DateUtil;
import uo.sdi.business.util.Asserts;
import uo.sdi.business.util.BusinessException;
import uo.sdi.business.util.Command;
import uo.sdi.dto.TaskDTO;
import uo.sdi.dto.util.Cloner;
import uo.sdi.model.Task;
import uo.sdi.model.User;
import uo.sdi.persistence.TaskFinder;
import uo.sdi.persistence.UserFinder;

/**
 * Este Action nos permite listar las tareas de un usuario por Today.
 * 
 * @author Amable y Carlos
 *
 */
public class ListTasksToday implements Command{

	private String login;

	public ListTasksToday(String login) {
		this.login = login;
	}

	@Override
	public Object execute() throws BusinessException {
		User user = UserFinder.findByLogin(login);
		Asserts.assertUserExist(user);
		Date tomorrow = new Date(DateUtil.tomorrow().getTime());
		List<Task> listTasks = TaskFinder.findBetween(user.getId(), 
				new Date(0), tomorrow);
		ArrayList<TaskDTO> listDTO = new ArrayList<TaskDTO>();
		for (Task task : listTasks) {
			listDTO.add(Cloner.clone(task));
		}
		return listDTO;
	}
}
