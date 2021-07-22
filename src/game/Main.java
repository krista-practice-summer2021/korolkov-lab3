package game;

import java.util.*;

import static game.InOutUtils.readStringsFromInputStream;
import static game.ProcessUtils.UTF_8;

/**
 * Main samplegame class.
 */
public class Main {

    public static void main(String[] args) {
        List<String> input = readStringsFromInputStream(System.in, UTF_8);
        if (!input.isEmpty()) {
            Round round = new Round(input);
            printMovingGroups(makeMove(round));
        }
        System.exit(0);
    }

    private static List<MovingGroup> makeMove(Round round) {
        List<MovingGroup> movingGroups = new ArrayList<>();
        List<Planet> planets = round.getPlanets();
        for (int i = 1; i < planets.size(); i++) {
            Planet current = planets.get(i);
            if (current.getOwnerTeam() == -1) {
                if (round.getDistanceMap()[i][i + 1] < 5) {
                    movingGroups.add(new MovingGroup(round.getTeamId(), current.getId(), current.getPopulation() + 10));
                }
            }
        }
        try {
            if (round.getOwnMovingGroups().size() > 1) {
                return movingGroups;
            }
        } catch (Exception e) {
        }

        int planetId = 0;
        int min = 100;
        List<Planet> workList = round.getAdversarysPlanets().size() > round.getNoMansPlanets().size() ? round.getAdversarysPlanets() : round.getNoMansPlanets();

        for (Planet adversarysPlanet : workList) {
            if ( adversarysPlanet.getPopulation()< min){
                min = adversarysPlanet.getPopulation();
                planetId = adversarysPlanet.getId();
            }
        }

        for (Planet ownPlanet : round.getOwnPlanets()) {

            movingGroups.add(new MovingGroup(ownPlanet.getId(), planetId, ownPlanet.getPopulation()/2));

        }


        return movingGroups;
    }

    private static void printMovingGroups(List<MovingGroup> moves) {
        System.out.println(moves.size());
        moves.forEach(move -> System.out.println(move.getFrom() + " " + move.getTo() + " " + move.getCount()));
    }

}
