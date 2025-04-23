
package da.model;


public class ChatLieu {
    private int id;
    private String tenChatLieu;

    public ChatLieu() {
    }

    public ChatLieu(int id, String tenChatLieu) {
        this.id = id;
        this.tenChatLieu = tenChatLieu;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTenChatLieu() {
        return tenChatLieu;
    }

    public void setTenChatLieu(String tenChatLieu) {
        this.tenChatLieu = tenChatLieu;
    }

    @Override
    public String toString() {
        return "ChatLieu{" + "id=" + id + ", tenChatLieu=" + tenChatLieu + '}';
    }
    
    
}
