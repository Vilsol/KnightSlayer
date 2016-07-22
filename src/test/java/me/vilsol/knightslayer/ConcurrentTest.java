package me.vilsol.knightslayer;

import com.mashape.unirest.http.Unirest;
import com.mashape.unirest.http.exceptions.UnirestException;
import org.json.JSONObject;
import org.junit.Test;

import java.util.OptionalDouble;
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

        int dragonsUsed = games.stream().mapToInt(Game::getBattleCount).sum();

        System.out.println();
        System.out.println("Finished all battles");
        System.out.println("Total dragons used: " + dragonsUsed);
        System.out.println("Victory Percentage: " + (((double) games.size() / (double) dragonsUsed) * 100) + "%");
        System.out.println("Average battle count of " + games.size() + " battles: " + games.stream().mapToInt(Game::getBattleCount).average().getAsDouble());

        for (Weather weather : Weather.values()) {
            int count = (int) games.stream().filter(g -> g.getWeather() == weather).count();
            OptionalDouble average = games.stream().filter(g -> g.getWeather() == weather).mapToInt(Game::getBattleCount).average();

            if(average.isPresent()) {
                System.out.println("Average battle count of " + count + " battles (" + weather.name() + "): " + average.getAsDouble());
            }
        }

    }

    public void play(JSONObject gameData, int id){
        Game game = new Game(gameData);

        if(game.getWeather() != Weather.STORM){
            game.win();
        }

        System.out.println(id + ": " + game);

        games.add(game);
    }

}
