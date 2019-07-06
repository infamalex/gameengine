package PhiEngine.util;

import java.util.Collection;

/**
 * Created by Alex on 30/10/2017.
 */
public class Tuple {
    static abstract class S0{
        public static final int SIZE = 0;
        private Object vals[];
        public S0(){
        }
        public Object get(int n){
            return vals[n];
        }
        public Object set(int n, Object value){
            Object temp = get(n);
            vals[n] = value;
            return temp;
        }
        public int size(){return 0;}

    }
    public static class S1<T1> extends S0{
        public static final int SIZE = 1;
        public S1(T1 t1){
            set(SIZE, t1);
        }
        public S1 set1(S1 s){
            return (S1)set(SIZE - 1, s);
        }
        public S1 get1(){
            return (S1)get(SIZE - 1);
        }
    }
    public static class S2<T1,T2> extends S1<T1> {

        public static final int SIZE = 2;
        public S2(T1 t1,T2 t2){
            super(t1);
            set(SIZE, t2);
        }
        public S2 set2(S2 s){
            return (S2)set(SIZE - 1, s);
        }
        public S2 get1(){
            return (S2)get(SIZE - 1);
        }
    }
    public static class S3<T1,T2,T3> extends S2<T1,T2> {

        public static final int SIZE = 4;
        public S3(T1 t1,T2 t2, T3 t3){
            super(t1,t2);
            set(SIZE, t3);
        }
        public S3 set3(S3 s){
            return (S3)set(SIZE - 1, s);
        }
        public S3 get3(){
            return (S3)get(SIZE - 1);
        }
    }

}
