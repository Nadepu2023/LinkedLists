public class Photo {
    private String name;
    private String photoDigest;
    private boolean viewed;

    public Photo(String name, String photoDigest) {
        this.name = name;
        this.photoDigest = photoDigest;
        this.viewed = false;
    }

    public void viewPhoto() {
        System.out.println("Now viewing " + name + ".");
        viewed = true;
    }

    public boolean equals(Photo other) {
        if (other == null){
            return false;
        } 
        return this.photoDigest.equals(other.photoDigest);
    }

    public boolean hasBeenViewed() {
        return viewed;
    }

    public String getName() {
        return name;
    }

    public String getDigest() {
        return photoDigest;
    }
}
