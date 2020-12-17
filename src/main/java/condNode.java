public class condNode {
        cond value;
        condNode left;
        condNode right;

       public condNode(cond value) {
            this.value = value;
            right = null;
            left = null;
        }
        public cond getCond(){
           return value;
        }
        public void addRight(cond value){
           this.right=new condNode(value);
        }
        public void addLeft(cond value){
           this.left=new condNode(value);
    }
}
