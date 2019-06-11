/**
 * @author Ben Forgy at touro
 * 11/13/13
 */
package mousecatcher;

import java.awt.Point;

class PointList {
    
    private int first;
    private int last;
    private int size;
    private Point[] pointArray;
    private boolean wasTraversed;
    
    PointList(int capacaty)
    {
        size = capacaty;
        pointArray = new Point[capacaty];
        first = 0;
        last = 0;
        for(int i = 0; i < size; i++)
        {
            pointArray[i] = new Point();
        }
    }
    /**
     * Adds the given point to the logically last position.
     * WARNING: Will erase old data.
     * @param Point thatPoint
     * @
     */
    public void add(Point thatPoint)
    {
        last = (last + 1) % size;
        if(wasTraversed)
            first = (first + 1) % size;
        pointArray[last].x = thatPoint.x;
        pointArray[last].y = thatPoint.y;
        
        if(last == size -1)
            wasTraversed = true;
    }
    /**
     * Returns in a First in last out format.
     * Meaning, index = 0 gets the last element entered.
     * @param int index
     * @return Point misslePosition
     */
    Point get(int index) {
        if(index < 0 || index >= size)
            throw new IndexOutOfBoundsException();
        return new Point(pointArray[(first+ (size - index - 1)) % size]);
    }   
}
