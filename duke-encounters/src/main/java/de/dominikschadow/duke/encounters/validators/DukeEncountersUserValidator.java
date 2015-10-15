/*
 * Copyright (C) 2015 Dominik Schadow, dominikschadow@gmail.com
 *
 * This file is part of the Application Intrusion Detection project.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *  http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package de.dominikschadow.duke.encounters.validators;

import com.google.common.base.Strings;
import de.dominikschadow.duke.encounters.Constants;
import de.dominikschadow.duke.encounters.domain.DukeEncountersUser;
import de.dominikschadow.duke.encounters.services.SecurityValidationService;
import de.dominikschadow.duke.encounters.services.UserService;
import org.owasp.appsensor.core.AppSensorClient;
import org.owasp.appsensor.core.DetectionPoint;
import org.owasp.appsensor.core.DetectionSystem;
import org.owasp.appsensor.core.Event;
import org.owasp.appsensor.core.event.EventManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;
import org.springframework.validation.beanvalidation.SpringValidatorAdapter;

import javax.inject.Named;

/**
 * @author Dominik Schadow
 */
@Named
public class DukeEncountersUserValidator implements Validator {
    @Autowired
    private SpringValidatorAdapter validator;
    @Autowired
    private AppSensorClient appSensorClient;
    @Autowired
    private EventManager ids;
    @Autowired
    private UserService userService;
    @Autowired
    private SecurityValidationService securityValidationService;

    @Override
    public boolean supports(Class<?> clazz) {
        return DukeEncountersUser.class.equals(clazz);
    }

    @Override
    public void validate(Object target, Errors errors) {
        validator.validate(target, errors);

        DukeEncountersUser user = (DukeEncountersUser) target;

        if (!Strings.isNullOrEmpty(user.getFirstname())) {
            if (securityValidationService.hasXssPayload(user.getFirstname())) {
                fireXssEvent();
                errors.rejectValue("firstname", Constants.XSS_ERROR_CODE, Constants.XSS_ERROR_MESSAGE);
            } else if (securityValidationService.hasSqlIPayload(user.getFirstname())) {
                fireSqlIEvent();
                errors.rejectValue("firstname", Constants.SQLI_ERROR_CODE, Constants.SQLI_ERROR_MESSAGE);
            }
        }

        if (!Strings.isNullOrEmpty(user.getLastname())) {
            if (securityValidationService.hasXssPayload(user.getLastname())) {
                fireXssEvent();
                errors.rejectValue("lastname", Constants.XSS_ERROR_CODE, Constants.XSS_ERROR_MESSAGE);
            } else if (securityValidationService.hasSqlIPayload(user.getLastname())) {
                fireSqlIEvent();
                errors.rejectValue("lastname", Constants.SQLI_ERROR_CODE, Constants.SQLI_ERROR_MESSAGE);
            }
        }

        if (!Strings.isNullOrEmpty(user.getUsername())) {
            if (securityValidationService.hasXssPayload(user.getUsername())) {
                fireXssEvent();
                errors.rejectValue("username", Constants.XSS_ERROR_CODE, Constants.XSS_ERROR_MESSAGE);
            } else if (securityValidationService.hasSqlIPayload(user.getUsername())) {
                fireSqlIEvent();
                errors.rejectValue("username", Constants.SQLI_ERROR_CODE, Constants.SQLI_ERROR_MESSAGE);
            }
        }

        if (!Strings.isNullOrEmpty(user.getEmail())) {
            if (securityValidationService.hasXssPayload(user.getEmail())) {
                fireXssEvent();
                errors.rejectValue("email", Constants.XSS_ERROR_CODE, Constants.XSS_ERROR_MESSAGE);
            } else if (securityValidationService.hasSqlIPayload(user.getEmail())) {
                fireSqlIEvent();
                errors.rejectValue("email", Constants.SQLI_ERROR_CODE, Constants.SQLI_ERROR_MESSAGE);
            }
        }
    }

    private void fireXssEvent() {
        DetectionPoint detectionPoint = new DetectionPoint(DetectionPoint.Category.INPUT_VALIDATION, "IE1");
        ids.addEvent(new Event(userService.getUser(), detectionPoint, getDetectionSystem()));
    }

    private void fireSqlIEvent() {
        DetectionPoint detectionPoint = new DetectionPoint(DetectionPoint.Category.COMMAND_INJECTION, "CIE1");
        ids.addEvent(new Event(userService.getUser(), detectionPoint, getDetectionSystem()));
    }

    private DetectionSystem getDetectionSystem() {
        return new DetectionSystem(appSensorClient.getConfiguration().getServerConnection()
                .getClientApplicationIdentificationHeaderValue());
    }
}
