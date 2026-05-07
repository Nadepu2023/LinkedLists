public class Album implements IAlbum {

    private static class Node {
        Photo data;
        Node next;
        Node prev;

        Node(Photo data) {
            this.data = data;
        }
    }

    private final String albumName;
    private Node current;  
    private Node oldest;   
    private int count;
    private boolean isOpen;

    public Album(String albumName) {
        this.albumName = albumName;
        this.current = null;
        this.oldest = null;
        this.count = 0;
        this.isOpen = false;
    }

    @Override
    public String getAlbumName() {
        return albumName;
    }

    @Override
    public int getCount() {
        return count;
    }

    @Override
    public boolean hasPhoto(Photo photo) {
        if (photo == null || current == null) {
            return false;
        }

        Node temp = current;
        do {
            if (temp.data.equals(photo)) {
                return true;
            }
            temp = temp.next;
        } while (temp != current);

        return false;
    }

    @Override
    public void addPhoto(Photo photo) {
        if (photo == null) {
            return;
        }        

        if (hasPhoto(photo)) {
            System.out.println("Attempted to add duplicate photo.");
            return;
        }

        Node newNode = new Node(photo);

        if (current == null) {
            current = newNode;
            oldest = newNode;
            newNode.next = newNode;
            newNode.prev = newNode;
            count = 1;
            return;
        }

        Node beforeOldest = oldest.prev;

        beforeOldest.next = newNode;
        newNode.prev = beforeOldest;

        newNode.next = oldest;
        oldest.prev = newNode;

        count++;
    }

    @Override
    public void deletePhoto(Photo photo) {
        if (photo == null || current == null) {
            System.out.println("Attempted to delete a photo that is not in the album.");
            return;
        }

        Node target = null;
        Node temp = current;

        do {
            if (temp.data.equals(photo)) {
                target = temp;
                break;
            }
            temp = temp.next;
        } while (temp != current);

        if (target == null) {
            System.out.println("Attempted to delete a photo that is not in the album.");
            return;
        }

        if (count == 1) {
            current = null;
            oldest = null;
            count = 0;
            return;
        }

        target.prev.next = target.next;
        target.next.prev = target.prev;

        if (target == current) {
            current = target.next;
        }

        if (target == oldest) {
            oldest = target.next;
        }

        count--;
    }

    @Override
    public boolean allPhotosViewed() {
        if (current == null) return false;

        Node temp = current;
        do {
            if (!temp.data.hasBeenViewed()) return false;
            temp = temp.next;
        } while (temp != current);

        return true;
    }

    @Override
    public boolean equals(IAlbum other) {
        if (other == null) return false;
        if (this.getCount() != other.getCount()) return false;

        if (this.current == null) return true; 

        Node temp = this.current;
        do {
            if (!other.hasPhoto(temp.data)) return false;
            temp = temp.next;
        } while (temp != this.current);

        return true;
    }

    @Override
    public void openAlbum() {
        System.out.println("Album " + albumName + " opened.");
        isOpen = true;

        if (current != null) {
            current.data.viewPhoto();
        }
    }

    @Override
    public void closeAlbum() {
        System.out.println("Album " + albumName + " closed.");
        isOpen = false;
    }

    @Override
    public void viewNextPhoto() {
        if (!isOpen) {
            System.out.println("Tried to view next photo, but album is closed.");
            return;
        }
        if (current == null) {
            System.out.println("Tried to view next photo, but album has no photos.");
            return;
        }

        current = current.next;
        current.data.viewPhoto();
    }

    @Override
    public void viewPreviousPhoto() {
        if (!isOpen) {
            System.out.println("Tried to view previous photo, but album is closed.");
            return;
        }
        if (current == null) {
            System.out.println("Tried to view previous photo, but album has no photos.");
            return;
        }

        current = current.prev;
        current.data.viewPhoto();
    }

    public static void main(String[] args) {
        Album a = new Album("Friends");

        Photo p1 = new Photo("mountains", "aaa");
        Photo p2 = new Photo("beach", "bbb");
        Photo p3 = new Photo("city", "ccc");

        a.addPhoto(p1);
        a.addPhoto(p2);
        a.addPhoto(p3);

        a.openAlbum();       
        a.viewNextPhoto();    
        a.viewPreviousPhoto();
        a.closeAlbum();

        a.addPhoto(new Photo("mountains_copy_name", "aaa")); 
    }
}
