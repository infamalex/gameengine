package PhiEngine.geom;

/**
 * Created by Alex on 25/12/2017.
 */
public abstract class ShapeIterator {

    public abstract int size();
    protected abstract Vector2D getPoint(int n);
    private int index = 0;

    public Vector2D next(){
        index = nextIndex();
        return getPoint(index);
    }



    public Vector2D previous() {

        index = previousIndex();
        return getPoint(index);
    }

    public int index() {
        return index;
    }

    public int nextIndex() {
        return (index+1)%size();
    }


    public int previousIndex() {
        return Math.floorMod(index-1,size());
    }

    public boolean first(){
        return index == 0;
    }

    public boolean last(){
        return  index == size() - 1;
    }

    public Vector2D get(){
        return getPoint(index);
    }

    public Vector2D toNext(){
        return getPoint(nextIndex()).sub(get());
    }
    public Vector2D toPrevious(){
        return getPoint(nextIndex()).sub(get());
    }


}

