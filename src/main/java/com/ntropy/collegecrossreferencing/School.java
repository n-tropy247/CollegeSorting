/*
 * Copyright (C) 2021 Ryan Castelli
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */
package com.ntropy.collegecrossreferencing;

import java.util.Comparator;

/**
 * Data class for a school.
 *
 * @author Ryan Castelli
 * @version 6.23.21
 * @since 6.22.21
 */
public class School {

    private final String PROF;
    private final String NAME;
    private final String STATE;
    private final String CITY;
    private final Coord COORDS;

    public School(String p, String n, Coord c, String s, String ci) {
        PROF = p;
        NAME = n;
        STATE = s;
        CITY = ci;
        COORDS = c;
    }

    public String getState() {
        return STATE;
    }

    public String getCity() {
        return CITY;
    }

    public String getProf() {
        return PROF;
    }

    public String getName() {
        return NAME;
    }

    public Coord getCoords() {
        return COORDS;
    }
}
