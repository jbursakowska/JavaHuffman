import java.util.*;

public class Main {

    final static String myString = "zaabbbccccdddd";

    /*
    Above string generates

    Key: a, frequency: 2
    Key: b, frequency: 3
    Key: c, frequency: 4
    Key: d, frequency: 4
    Key: z, frequency: 1
     */

    private static Map<Character, Integer> characterCount;
    private static ArrayList<Tree> treeList;
    private static Tree mainTree;
    private static String encodedString = "";

    public static void main(String [] args){

        characterCount = new HashMap<>();
        treeList = new ArrayList<>();
        mainTree = null;

        countCharacters();

        huffman();
    }

    // create a HashMap that stores character as a key and frequency of that character as the value
    public static void countCharacters(){

        // http://stackoverflow.com/questions/11229986/get-string-character-by-index-java

        for (int i = 0; i < myString.length(); i++){

            // check if character is in the map
            if (characterCount.get(myString.charAt(i)) != null){
                characterCount.put(myString.charAt(i), characterCount.get(myString.charAt(i)) + 1); // increment if it is
            } else {
                characterCount.put(myString.charAt(i), 1); // set to one if this is the first occurrence
            }
        }
    }

    // code for Huffman's algorithm
    public static void huffman(){

        // http://stackoverflow.com/questions/3605237/how-print-out-the-contents-of-a-hashmapstring-string-in-ascending-order-based

        List<Character> keys = new ArrayList<>(characterCount.keySet());

        // create an ArrayList of single node trees (initial step p. 436 figure 10.13)
        for (int i = 0; i < keys.size(); i++){

            Character key = keys.get(i);

            // print frequencies
            System.out.println("Key: " + key + ", frequency: " + characterCount.get(key));

            // create initial trees
            treeList.add(new Tree(key, "" ,characterCount.get(key)));
        }

        // sort ArrayList of trees by frequency
        insertionSort(treeList);

        // perform merges
        while (treeList.size() > 1){
            Tree temp = new Tree();

            Tree left = treeList.remove(0);
            Tree right = treeList.remove(0);

            temp.root.left = left.root;
            temp.root.right = right.root;
            temp.root.frequency = left.root.frequency + right.root.frequency;

            treeList.add(temp);
            insertionSort(treeList);
        }

        // only 1 tree is left, this is the final tree
        mainTree = treeList.get(0);

        // traverse the tree and generate codes for each character based on direction of traversal
        // 0 = left, 1 = right
        generateCodes();
    }

    public static void generateCodes(){
        traverse(mainTree.root);
        printEncodedString(mainTree.root);
    }

    // http://stackoverflow.com/questions/10117136/traversing-a-binary-tree-recursively

    // sets binary code based on 0 = left 1 = right
    public static void traverse(Tree.Node root){
        if (root.left != null){
            root.left.code = root.code + "0";
            traverse(root.left);
        }
        if (root.right != null){
            root.right.code = root.code + "1";
            traverse(root.right);
        }
    }

    // returns binary code based on 0 = left 1 = right
    public static void traverseAndReturnCode(Tree.Node root, Character character){
        if (root.left != null){
            if (root.left.character == character){
                encodedString += " " + root.left.code;
            } else {
                traverseAndReturnCode(root.left, character);
            }
        }
        if (root.right != null){
            if (root.right.character == character){
                encodedString += " " + root.right.code;
            } else {
                traverseAndReturnCode(root.right, character);
            }
        }
    }

    // returns binary code based on 0 = left 1 = right
    public static void traverseAndReturnLetter(Tree.Node root, Character character){
        if (root.left != null){
            if (root.left.character == character){
                encodedString += " " + root.left.code;
            } else {
                traverseAndReturnCode(root.left, character);
            }
        }
        if (root.right != null){
            if (root.right.character == character){
                encodedString += " " + root.right.code;
            } else {
                traverseAndReturnCode(root.right, character);
            }
        }
    }

    public static void printEncodedString(Tree.Node root){

        for (int i = 0; i < myString.length(); i++){
            traverseAndReturnCode(root, myString.charAt(i));
        }

        System.out.println(myString + " - encoded value = " + encodedString);
    }

    // modified insertion sort from p. 273
    public static void insertionSort(ArrayList<Tree> list){

        int j;

        for (int p = 1; p < list.size(); p++){

            Tree tmp = list.get(p);

            for (j = p; j > 0 && tmp.root.frequency < list.get(j-1).root.frequency; j--){
                list.set(j, list.get(j-1));
            }
            list.set(j, tmp);
        }
    }
}
