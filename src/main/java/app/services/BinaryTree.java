package app.services;

// AVL Binary Tree or Balanced Binary Tree

import app.models.Client;

public class BinaryTree {

    private NodeTree root;
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

    private NodeTree insert(NodeTree current, Client client) {
        if (current == null) {
            size++;
            return new NodeTree(client);
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

        NodeTree current = root;

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

    private NodeTree delete(NodeTree current, String documentClient) {
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

            NodeTree successor = findMin(current.right);

            current.client = successor.client;

            current.right = delete(
                    current.right,
                    successor.client.getDocumentClient());
        }

        return balance(current);
    }

    // FIND MINIMUM
    private NodeTree findMin(NodeTree current) {
        while (current.left != null) {
            current = current.left;
        }

        return current;
    }

    // AVL BALANCING
    private NodeTree balance(NodeTree NodeTree) {
        updateHeight(NodeTree);

        int balanceFactor = getBalanceFactor(NodeTree);

        if (balanceFactor > 1) {
            if (getBalanceFactor(NodeTree.left) < 0) {
                NodeTree.left = rotateLeft(NodeTree.left);
            }

            return rotateRight(NodeTree);
        }

        if (balanceFactor < -1) {
            if (getBalanceFactor(NodeTree.right) > 0) {
                NodeTree.right = rotateRight(NodeTree.right);
            }

            return rotateLeft(NodeTree);
        }

        return NodeTree;
    }

    private int height(NodeTree NodeTree) {
        return NodeTree == null ? 0 : NodeTree.height;
    }

    private void updateHeight(NodeTree NodeTree) {
        NodeTree.height = 1 + Math.max(
                height(NodeTree.left),
                height(NodeTree.right));
    }

    private int getBalanceFactor(NodeTree NodeTree) {
        return height(NodeTree.left) - height(NodeTree.right);
    }

    private NodeTree rotateRight(NodeTree y) {
        NodeTree x = y.left;
        NodeTree subtree = x.right;

        x.right = y;
        y.left = subtree;

        updateHeight(y);
        updateHeight(x);

        return x;
    }

    private NodeTree rotateLeft(NodeTree x) {
        NodeTree y = x.right;
        NodeTree subtree = y.left;

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
            NodeTree current,
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

class NodeTree {
    Client client;
    NodeTree left;
    NodeTree right;
    int height;

    NodeTree(Client client) {
        this.client = client;
        this.left = null;
        this.right = null;
        this.height = 1;
    }
}
