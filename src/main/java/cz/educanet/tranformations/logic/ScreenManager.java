package cz.educanet.tranformations.logic;

import cz.educanet.tranformations.logic.models.Coordinate;

import java.util.HashSet;
import java.util.Set;

public class ScreenManager {

    private Set<Coordinate> selectedPoints = new HashSet<>();

    public void select(Coordinate coordinate) {
        selectedPoints.add(coordinate);
    }

    public void unselect(Coordinate coordinate) {
        selectedPoints.remove(coordinate);
    }

    public boolean isSelected(Coordinate coordinate) {
        return selectedPoints.contains(coordinate);
    }

    public Set<Coordinate> getSelectedPoints() {
        return selectedPoints;
    }

    public Coordinate[] getCoordinate() {
        return selectedPoints.toArray(Coordinate[]::new);
    }

    public boolean isFilledIn(Coordinate coordinate) {
        if (selectedPoints.size() != 3) {
            return false;
        }
        return v_uhlu(getCoordinate()[0], this.getCoordinate()[1], this.getCoordinate()[2], coordinate) &&
                v_uhlu(getCoordinate()[1], this.getCoordinate()[2], this.getCoordinate()[0], coordinate) &&
                v_uhlu(getCoordinate()[2], this.getCoordinate()[0], this.getCoordinate()[1], coordinate);
    }

    // Zjistí, zda body A a B lezi ve stejne polorovine od primky u1x+u2y+u3=0
    public boolean porovnej(int u1, int u2, int u3, int a1, int a2, int b1, int b2) {
        return ((u1*a1 + u2*a2 + u3) * (u1*b1 + u2*b2 + u3) > 0);
    }

    // Kontroluje, zda lezi bod D[d1, d2] v uhlu ABC
    public boolean v_uhlu(Coordinate a, Coordinate b, Coordinate c, Coordinate d) {
        int[] u = {(-b.getY() + a.getY()), (b.getX() - a.getX()), (0)};//primka AB - dosadit A, kontrola s C
        int[] v = {(-c.getY() + b.getY()), (c.getX() - b.getX()), (0)};//primka BC - dosadit B, kontrola s A
        u[2] = -u[0] * a.getX() - u[1] * a.getY();
        v[2] = -v[0] * b.getX() - v[1] * b.getY();
        return ((porovnej(u[0], u[1], u[2], c.getX(), c.getY(), d.getX(), d.getY())) && (porovnej(v[0], v[1], v[2], a.getX(), a.getY(), d.getX(), d.getY())));
    }

    // https://maths.cz/clanky/124-analyticka-geometrie-poloha-bodu-vuci-primce
    // i have no fricking clue how this works x)
    // isFilledMetoda half yoinked od Mikuláše
}
