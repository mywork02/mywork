package com.suime.library.servlet;

import com.confucian.framework.ioc.SpringContext;
import com.suime.context.model.Listening;
import com.suime.library.service.ListeningService;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.math.NumberUtils;
import org.apache.http.HttpStatus;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Created by ice on 25/11/2015.
 */
@WebServlet({"/mp3Statistic"})
public class Mp3StatisticsServlet extends HttpServlet {

    @Override
    protected void service(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        ListeningService listeningService = (ListeningService) SpringContext.getBean("listeningService");
        String sId = req.getParameter("id");
        Listening listening = null;
        if (StringUtils.isBlank(sId)) {
            resp.setStatus(HttpStatus.SC_BAD_REQUEST);
            return;
        }
        Long id = NumberUtils.toLong(sId, 0);
        listeningService.updateTimeById(id);
        listening = listeningService.fetchById(id);
        if (listening != null) {
            resp.sendRedirect(listening.getRedirectUrl());
        } else {
            resp.setStatus(HttpStatus.SC_NOT_FOUND);
        }
    }
}
