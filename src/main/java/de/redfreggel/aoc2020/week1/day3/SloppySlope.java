package de.redfreggel.aoc2020.week1.day3;

public class SloppySlope {

    private final int posToMoveRight;
    private final int posToMoveDown;
    private int hitByTreeCounter= 0;
    private int actualPositionRight =0;
    private int actualPositionLine =0;

    public SloppySlope(int pPosToMoveRight, int pPosToMoveDown){
        this.posToMoveDown = pPosToMoveDown;
        this.posToMoveRight = pPosToMoveRight;
    }

    public void hitByTree(){
        hitByTreeCounter++;
    }

    public void moveRight(){
        actualPositionRight = actualPositionRight+posToMoveRight;
    }

    public void moveDownByOne(){
        actualPositionLine ++;
    }

    public int getActualRightPosition(){
        return actualPositionRight;
    }

    public int getHitByTreeCounter(){
        return this.hitByTreeCounter;
    }

    public boolean hasToCheck(){
        return this.actualPositionLine%this.posToMoveDown==0;
    }

    public int getBufferedRightPosition() {
        return  this.getActualRightPosition()+posToMoveRight;
    }
}
