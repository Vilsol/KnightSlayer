package me.vilsol.knightslayer;

import com.mashape.unirest.http.Unirest;
import com.mashape.unirest.http.exceptions.UnirestException;
import org.json.JSONObject;
import org.junit.Test;

import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadPoolExecutor;

public class ConcurrentTest {

    private static CopyOnWriteArrayList<Game> games = new CopyOnWriteArrayList<>();

    @Test
    public void concurrentTest() throws UnirestException, InterruptedException {
        int gameCount = 10000;
        int threadCount = 100;

        ThreadPoolExecutor executor = (ThreadPoolExecutor) Executors.newFixedThreadPool(threadCount);
        for (int i = 0; i < gameCount; i++) {
            int id = i;
            Runnable worker = () -> {
                try {
                    JSONObject gameData = Unirest.get("http://www.dragonsofmugloar.com/api/game").asJson().getBody().getObject();
                    play(gameData, id);
                } catch (UnirestException e) {
                    e.printStackTrace();
                }
            };
            executor.execute(worker);
        }

        executor.shutdown();
        while (!executor.isTerminated()) {
            Thread.sleep(100);
        }

        Thread.sleep(100);

        long wonGames = games.stream().filter(g -> g.getBattle().getGameStatus() == GameStatus.VICTORY).count();

        System.out.println();
        System.out.println("Finished all battles");
        System.out.println("Games played: " + games.size());
        System.out.println("Won games: " + wonGames);
        System.out.println("Victory Percentage: " + (((double) wonGames / (double) games.size()) * 100) + "%");
    }

    public void play(JSONObject gameData, int id){
        Game game = new Game(gameData);
        game.win();
        System.out.println(id + ": " + game.getBattle().getGameStatus() + " - " + game.getKnight() + " VS " + game.getDragon());
        games.add(game);
    }

}
