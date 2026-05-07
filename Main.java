public class Main {
    public static void main(String[] args) {
        Album album = new Album("My Album");
        Photo photo1 = new Photo("photo1.jpg", "digest1");
        Photo photo2 = new Photo("photo2.jpg", "digest2");

        album.addPhoto(photo1);
        album.addPhoto(photo2);

        System.out.println("Album Name: " + album.getAlbumName());
        System.out.println("Photo Count: " + album.getCount());
    }
}