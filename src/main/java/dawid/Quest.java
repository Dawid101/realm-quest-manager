package dawid;

public class Quest {
    private Long id;
    private String title;
    private int rewardCoins;
    private boolean completed;

    public Quest() {
    }

    public Quest(Long id, String title, int rewardCoins) {
        this.id = id;
        this.title = title;
        this.rewardCoins = rewardCoins;
    }

    public Long getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public int getRewardCoins() {
        return rewardCoins;
    }

    public boolean isCompleted() {
        return completed;
    }

    public void setCompleted(boolean completed) {
        this.completed = completed;
    }

    @Override
    public String toString() {
        return "Quest{" +
                "title='" + title + '\'' +
                ", completed=" + completed +
                ", rewardCoins=" + rewardCoins +
                '}';
    }
}
