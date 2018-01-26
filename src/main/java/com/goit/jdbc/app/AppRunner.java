package com.goit.jdbc.app;

import com.goit.jdbc.app.dao.DAOBase;
import com.goit.jdbc.app.dao.Developer;
import com.goit.jdbc.app.dao.JdbcDeveloperDao;

public class AppRunner {
    private DAOBase<Developer, Long> developerDao;

    public AppRunner() {
        developerDao = new JdbcDeveloperDao();
    }
}
