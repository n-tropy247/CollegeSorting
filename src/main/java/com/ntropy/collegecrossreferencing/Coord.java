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

/**
 * Data class for GPS coordinates.
 *
 * @author Ryan Castelli
 * @version 6.23.21
 * @since 6.22.21
 */
public class Coord {

    private final double LAT;
    private final double LONG;

    public Coord(double la, double lo) {
        LAT = la;
        LONG = lo;
    }

    public double getLat() {
        return LAT;
    }

    public double getLong() {
        return LONG;
    }

    @Override
    public String toString() {
        return LAT + ", " + LONG;
    }
}
