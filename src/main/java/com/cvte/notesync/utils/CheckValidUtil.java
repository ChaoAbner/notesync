package com.cvte.notesync.utils;

import com.alibaba.fastjson.JSONObject;
import com.cvte.notesync.common.enums.NoteHttpStatus;
import com.cvte.notesync.common.exception.NoteException;
import com.cvte.notesync.constant.RestfulPath;
import com.cvte.notesync.constant.ValidValue;
import org.springframework.web.servlet.HandlerMapping;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;

@SuppressWarnings("ALL")
public class CheckValidUtil {

    public static void check(HttpServletRequest request, JSONObject jsonObject) {
        String servletPath = request.getServletPath();
        if (servletPath.startsWith(RestfulPath.NOTE_PATH) ||
                servletPath.startsWith(RestfulPath.SYNC_PATH)) {
            checkNote(jsonObject);
        } else if (servletPath.startsWith(RestfulPath.SESSION_PATH)) {
            checkUsername(request);
        }
    }

    public static void checkNote(JSONObject jsonObject) {
        String content = (String) jsonObject.get(ValidValue.CONTENT_FIELD);
        String title = (String) jsonObject.get(ValidValue.TITLE_FIELD);

        if (content.length() > ValidValue.NOTE_CONTENT_MAX_LENGTH ||
            title.length() > ValidValue.NOTE_TITLE_MAX_LENGTH) {
            throw new NoteException(NoteHttpStatus.PARAMETER_ERROR);
        }
    }

    public static void checkUsername(HttpServletRequest request) {
        Map map = (Map) request.getAttribute(HandlerMapping.URI_TEMPLATE_VARIABLES_ATTRIBUTE);
        String username = (String) map.get(ValidValue.SESSION_FIELD);

        if (username.length() > ValidValue.USERNAME_MAX_LENGTH) {
            throw new NoteException(NoteHttpStatus.PARAMETER_ERROR);
        }
    }
}
