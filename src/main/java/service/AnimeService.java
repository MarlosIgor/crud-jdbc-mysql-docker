package service;

import dominio.Anime;
import dominio.Producer;
import repository.AnimeRepository;
import java.util.Optional;
import java.util.Scanner;

public class AnimeService {

    private static final Scanner SCANNER = new Scanner(System.in);

    public static void menu(int op) {
        switch (op) {
            case 1 -> findByName();
            case 2 -> delete();
            case 3 -> save();
            case 4 -> update();
        }
    }

    private static void findByName() {
        System.out.println("Digite o nome para todos");
        String name = SCANNER.nextLine();
        AnimeRepository.findByName(name)
                .forEach(p -> System.out.printf("[%d] -%s %d %s%n",
                        p.getId(),
                        p.getName(),
                        p.getEpisodes(),
                        p.getProducer().getName()));
    }

    private static void delete() {
        System.out.println("Digite o id do anime que você deseja excluir");
        int id = Integer.parseInt(SCANNER.nextLine());
        System.out.println("Tem certeza? Y/N");
        String choice = SCANNER.nextLine();
        if ("y".equalsIgnoreCase(choice)) {
            AnimeRepository.delete(id);
        }
    }

    private static void save() {
        System.out.println("Digite o nome do anime");
        String name = SCANNER.nextLine();
        System.out.println("Digite o número de episódios");
        int episodes = Integer.parseInt(SCANNER.nextLine());
        System.out.println("Digite o id do producer");
        Integer producerId = Integer.parseInt(SCANNER.nextLine());
        Anime anime = Anime.builder()
                .episodes(episodes)
                .name(name)
                .producer(Producer.builder().id(producerId).build())
                .build();
        AnimeRepository.save(anime);
    }

    private static void update() {
        System.out.println("Digite o id do objeto que você deseja atualizar");
        Optional<Anime> animeOptinal = AnimeRepository.findById(Integer.parseInt(SCANNER.nextLine()));
        if (animeOptinal.isEmpty()) {
            System.out.println("Anime não encontrado");
            return;
        }

        Anime animeFromDb = animeOptinal.get();
        System.out.printf("Anime encontrado %s%n", animeFromDb);
        System.out.println("Digite o novo nome ou digite para manter o mesmo");
        String name = SCANNER.nextLine();
        name = name.isEmpty() ? animeFromDb.getName() : name;

        System.out.println("Digite o número de episódios");
        int episodes = Integer.parseInt(SCANNER.nextLine());
        Anime animeToUpdate = Anime.builder()
                .id(animeFromDb.getId())
                .episodes(episodes)
                .producer(animeFromDb.getProducer())
                .name(name)
                .build();
        AnimeRepository.update(animeToUpdate);
    }
}
