import jakarta.persistence.EntityManager;
import org.example.entity.User;
import org.example.repository.UserRepository;
import org.example.service.UserService;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static org.mockito.Mockito.*;

@DataJpaTest// MOCKITO: Liga o Mockito ao JUnit 5
class UserTests {

    @Mock // MOCKITO: Cria objeto falso do repositório
    private UserRepository userRepository;

    @InjectMocks // MOCKITO: Injeta o mock no UserService
    private UserService userService;

    @Autowired
    private EntityManager entityManager;

    private List<User> usuariosPadrao;

    @BeforeEach // JUNIT: Executa ANTES de cada teste - útil para setup comum
    void setUp() {
        usuariosPadrao = Arrays.asList(
                new User(1L, "João", "joao@email.com", "jo", true, LocalDateTime.now()),
                new User(2L, "Maria", "maria@email.com", "ma", true, LocalDateTime.now())
        );

        System.out.println("preparando teste");
    }

    private void criarUsuariosPadrao() {
        usuariosPadrao.forEach(usuario-> entityManager.persist(usuario));
    }



    @Test
    void deveBuscarTodosUsuarios() {

        when(userRepository.findAll()).thenReturn(usuariosPadrao);

        List<User> resultado = userService.getAllUsers();

        Assertions.assertNotNull(resultado);
        Assertions.assertEquals(2, resultado.size());
        Assertions.assertEquals("João", resultado.get(0).getName());

        verify(userRepository).findAll();
    }


    @Test
    void deveRetornarListaVaziaQuandoNaoHaUsuarios() {
        when(userRepository.findAll()).thenReturn(Collections.emptyList());
        List<User> resultado = userService.getAllUsers();
        Assertions.assertNotNull(resultado);
        Assertions.assertTrue(resultado.isEmpty());
    }


    @Test
    void deveLancarExcecaoQuandoRepositorioFalhar() {
        when(userRepository.findAll())
                .thenThrow(new RuntimeException("Erro ao conectar no banco"));

        RuntimeException exception = Assertions.assertThrows(RuntimeException.class, () -> {
            userService.getAllUsers();
        });

        Assertions.assertEquals("Erro ao conectar no banco", exception.getMessage());
    }


    @Test
    void deveValidarTodosCamposDoUsuario() {
        when(userRepository.findAll()).thenReturn(usuariosPadrao);

        List<User> resultado = userService.getAllUsers();
        User primeiroUsuario = resultado.get(0);

        Assertions.assertAll("Verificações do primeiro usuário",
                () -> Assertions.assertEquals("João", primeiroUsuario.getName()),
                () -> Assertions.assertEquals("joao@email.com", primeiroUsuario.getEmail()),
                () -> Assertions.assertEquals("jo", primeiroUsuario.getUsername()),
                () -> Assertions.assertTrue(primeiroUsuario.getEmailNotifications())
        );
    }


    @Test
    void deveVerificarQuantidadeDeChamadas() {
        when(userRepository.findAll()).thenReturn(usuariosPadrao);

        userService.getAllUsers();
        userService.getAllUsers();

        // MOCKITO: Verificar que foi chamado 2 vezes
        verify(userRepository, times(2)).findAll();
    }

    @Test
    void naoDeveChamarRepositorio() {
        verify(userRepository, never()).findAll();
    }


    @Test
    void deveUsarAnyMatchersEmMetodosComParametros() {
        User novoUser = new User(null, "Novo", "novo@email.com", "nv", true, LocalDateTime.now());

        when(userRepository.save(any(User.class))).thenReturn(novoUser);

        // anyLong() - qualquer Long
        // anyString() - qualquer String
        // anyList() - qualquer Lista

         verify(userRepository).save(any(User.class));
    }


    @Test
    void deveCapturarEValidarArgumentoPassado() {
        User novoUser = new User(null, "Capturado", "cap@email.com", "cp", true, LocalDateTime.now());

        ArgumentCaptor<User> captor = ArgumentCaptor.forClass(User.class);

        when(userRepository.save(any(User.class))).thenReturn(novoUser);

         verify(userRepository).save(captor.capture());

         User capturado = captor.getValue();
         Assertions.assertEquals("Capturado", capturado.getName());
         Assertions.assertNotNull(capturado.getCreatedAt());
    }



    @Test
    void deveRetornarDiferenteEmCadaChamada() {
        List<User> lista1 = Arrays.asList(
                new User(1L, "Cache", "cache@email.com", "c1", true, LocalDateTime.now())
        );

        List<User> lista2 = Arrays.asList(
                new User(2L, "Atualizado", "atualizado@email.com", "c2", true, LocalDateTime.now()),
                new User(3L, "Novo", "novo@email.com", "c3", true, LocalDateTime.now())
        );


        when(userRepository.findAll())
                .thenReturn(lista1)
                .thenReturn(lista2);

        Assertions.assertEquals(1, userService.getAllUsers().size());
        Assertions.assertEquals(2, userService.getAllUsers().size());
    }
}

