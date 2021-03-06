/*
 * Copyright (C) 2016 Dominik Schadow, dominikschadow@gmail.com
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
package de.dominikschadow.dukeencounters.encounter;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.NotBlank;

import javax.persistence.*;

/**
 * Holds a role in the application.
 *
 * @author Dominik Schadow
 */
@Entity
@Table(name = "authorities", uniqueConstraints = @UniqueConstraint(columnNames = {"username"}))
@Data
@NoArgsConstructor
public class Authority {
    @Id
    @GeneratedValue
    private long id;
    @NotBlank(message = "Username may not be null")
    private String username;
    @NotBlank(message = "Authority may not be null")
    private String authority;

    public Authority(final String username, final String authority) {
        this.username = username;
        this.authority = authority;
    }
}
