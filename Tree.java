public class Tree {

    public Node root;

    public Tree(){
        root = new Node();
        root.code = "";
    }

    public Tree(char character, String code, int frequency){
        root = new Node(character, code, frequency);
    }

    public class Node {

        public char character;
        public String code;
        public Node left;
        public Node right;
        public int frequency;

        public Node(){

        }

        public Node(char character, String code, int frequency){
            this.character = character;
            this.code = code;
            this.frequency = frequency;
            this.left = null;
            this.right = null;
        }
    }
}