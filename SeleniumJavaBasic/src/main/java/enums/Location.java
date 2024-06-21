package enums;

public enum Location {
    SAI_GON("Sài Gòn"),
    PHAN_THIET("Phan Thiết"),
    NHA_TRANG("Nha Trang"),
    DA_NANG("Đà Nẵng"),
    HUE("Huế"),
    QUANG_NGAI("Quãng Ngãi");

    private final String location;

    Location(String location) {
        this.location = location;
    }

    public String getLocation() {
        return location;
    }
}

