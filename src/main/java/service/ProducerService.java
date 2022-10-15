package service;

import dominio.Producer;
import repository.ProducerRepository;
import java.util.Optional;
import java.util.Scanner;

public class ProducerService {

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
        ProducerRepository.findByName(name).forEach(p -> System.out.printf("[%d] -%s%n", p.getId(), p.getName()));
    }

    private static void delete() {
        System.out.println("Digite o id do producer que você deseja excluir");
        int id = Integer.parseInt(SCANNER.nextLine());
        System.out.println("Tem certeza? Y/N");
        String choice = SCANNER.nextLine();
        if ("y".equalsIgnoreCase(choice)) {
            ProducerRepository.delete(id);
        }
    }

    private static void save() {
        System.out.println("Digite o nome do producer");
        String name = SCANNER.nextLine();
        Producer producer = Producer.builder().name(name).build();
        ProducerRepository.save(producer);
    }

    private static void update() {
        System.out.println("Digite o id do objeto que você deseja atualizar");
        Optional<Producer> producerOptinal = ProducerRepository.findById(Integer.parseInt(SCANNER.nextLine()));
        if (producerOptinal.isEmpty()) {
            System.out.println("Producer não encontrado");
            return;
        }

        Producer producerFromDb = producerOptinal.get();
        System.out.printf("producer encontrado %s%n", producerFromDb);
        System.out.println("Digite o novo nome ou digite para manter o mesmo");
        String name = SCANNER.nextLine();
        name = name.isEmpty() ? producerFromDb.getName() : name;

        Producer producerToUpdate = Producer.builder()
                .id(producerFromDb.getId())
                .name(name)
                .build();

        ProducerRepository.update(producerToUpdate);
    }
}
