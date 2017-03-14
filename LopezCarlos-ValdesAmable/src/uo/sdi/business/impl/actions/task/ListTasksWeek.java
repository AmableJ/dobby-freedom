package uo.sdi.business.impl.actions.task;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import alb.util.date.DateUtil;
import uo.sdi.business.util.BusinessException;
import uo.sdi.business.util.Command;
import uo.sdi.dto.TaskDTO;
import uo.sdi.dto.util.Cloner;
import uo.sdi.model.Task;
import uo.sdi.model.User;
import uo.sdi.persistence.TaskFinder;
import uo.sdi.persistence.UserFinder;

public class ListTasksWeek implements Command{

	private String login;

	public ListTasksWeek(String login) {
		this.login = login;
	}

	@Override
	public Object execute() throws BusinessException {
		User user = UserFinder.findByLogin(login);
		assertUserExist(user);
		List<Task> listTasks = TaskFinder.findBetween(user.getId(),
				DateUtil.today(),
				new Date(System.currentTimeMillis() + 518400000));
		ArrayList<TaskDTO> listDTO = new ArrayList<TaskDTO>();
		for (Task task : listTasks) {
			listDTO.add(Cloner.clone(task));
		}
		return listDTO;
	}

	private void assertUserExist(User user) throws BusinessException {
		if (user != null) return;
		throw new BusinessException("El usuario no existe");
	}
}
