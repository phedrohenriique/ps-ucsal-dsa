package app.services;

import app.models.Client;

public class BinaryTree {

    private Node root;
    private int size;

    public BinaryTree() {
        this.root = null;
        this.size = 0;
    }

    // INSERT
    public void insert(Client client) {
        if (client == null || client.getDocumentClient() == null) {
            throw new IllegalArgumentException("Invalid client");
        }

        root = insert(root, client);
    }

    private Node insert(Node current, Client client) {
        if (current == null) {
            size++;
            return new Node(client);
        }

        int comparison = client.getDocumentClient()
                .compareTo(current.client.getDocumentClient());

        if (comparison < 0) {
            current.left = insert(current.left, client);
        } else if (comparison > 0) {
            current.right = insert(current.right, client);
        } else {
            // Document already exists: replace client
            current.client = client;
            return current;
        }

        return balance(current);
    }

    // SEARCH
    public Client search(String documentClient) {
        if (documentClient == null) {
            return null;
        }

        Node current = root;

        while (current != null) {
            int comparison = documentClient.compareTo(
                    current.client.getDocumentClient());

            if (comparison == 0) {
                return current.client;
            }

            current = comparison < 0
                    ? current.left
                    : current.right;
        }

        return null;
    }

    // DELETE
    public void delete(String documentClient) {
        if (search(documentClient) != null) {
            root = delete(root, documentClient);
            size--;
        }
    }

    private Node delete(Node current, String documentClient) {
        if (current == null) {
            return null;
        }

        int comparison = documentClient.compareTo(
                current.client.getDocumentClient());

        if (comparison < 0) {
            current.left = delete(current.left, documentClient);
        } else if (comparison > 0) {
            current.right = delete(current.right, documentClient);
        } else {
            if (current.left == null) {
                return current.right;
            }

            if (current.right == null) {
                return current.left;
            }

            Node successor = findMin(current.right);

            current.client = successor.client;

            current.right = delete(
                    current.right,
                    successor.client.getDocumentClient());
        }

        return balance(current);
    }

    // FIND MINIMUM
    private Node findMin(Node current) {
        while (current.left != null) {
            current = current.left;
        }

        return current;
    }

    // AVL BALANCING
    private Node balance(Node node) {
        updateHeight(node);

        int balanceFactor = getBalanceFactor(node);

        if (balanceFactor > 1) {
            if (getBalanceFactor(node.left) < 0) {
                node.left = rotateLeft(node.left);
            }

            return rotateRight(node);
        }

        if (balanceFactor < -1) {
            if (getBalanceFactor(node.right) > 0) {
                node.right = rotateRight(node.right);
            }

            return rotateLeft(node);
        }

        return node;
    }

    private int height(Node node) {
        return node == null ? 0 : node.height;
    }

    private void updateHeight(Node node) {
        node.height = 1 + Math.max(
                height(node.left),
                height(node.right));
    }

    private int getBalanceFactor(Node node) {
        return height(node.left) - height(node.right);
    }

    private Node rotateRight(Node y) {
        Node x = y.left;
        Node subtree = x.right;

        x.right = y;
        y.left = subtree;

        updateHeight(y);
        updateHeight(x);

        return x;
    }

    private Node rotateLeft(Node x) {
        Node y = x.right;
        Node subtree = y.left;

        y.left = x;
        x.right = subtree;

        updateHeight(x);
        updateHeight(y);

        return y;
    }

    // AUXILIARY METHODS
    public int size() {
        return size;
    }

    public boolean isEmpty() {
        return root == null;
    }

    public Client[] getAll() {
        Client[] clients = new Client[size];

        int[] index = { 0 };

        inOrderTraversal(root, clients, index);

        return clients;
    }

    private void inOrderTraversal(
            Node current,
            Client[] clients,
            int[] index) {
        if (current == null) {
            return;
        }

        inOrderTraversal(current.left, clients, index);

        clients[index[0]] = current.client;
        index[0]++;

        inOrderTraversal(current.right, clients, index);
    }

}

class Node {
    Client client;
    Node left;
    Node right;
    int height;

    Node(Client client) {
        this.client = client;
        this.left = null;
        this.right = null;
        this.height = 1;
    }
}
