package TaskApp.demo.logic;

import TaskApp.demo.model.TaskGroup;
import TaskApp.demo.model.TaskGroupRepository;
import TaskApp.demo.model.TaskRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.Optional;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.assertj.core.api.ThrowableAssert.catchThrowable;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class TaskGroupServiceTest {

    @Test
    @DisplayName("toggleGroup throws Illegal state exception when existByDoneAndGroupId is thue ")
    void toggleGroup_existByDoneAndGroupIsTrue_throwsIllegalStateException() {
        //given
        var mockTaskRepository = mock(TaskRepository.class);
        when(mockTaskRepository.existsByDoneIsFalseAndGroup_Id(anyInt()))
                .thenReturn(true);
        //system under test
        var toTest = new TaskGroupService(null, mockTaskRepository);
        //when
        var exception = catchThrowable(() -> toTest.toggleGroup(1));
        //then
        assertThat(exception)
                .isInstanceOf(IllegalStateException.class)
                .hasMessageContaining("has undone tasks");
    }

    @Test
    @DisplayName("toggleGroup repository returns empty optional should throw Illegal state Exception")
    void toggleGroup_repositoryFindById_returnEmptyOptional() {
        //given
        var mockTaskRepository = mock(TaskRepository.class);
        when(mockTaskRepository.existsByDoneIsFalseAndGroup_Id(anyInt()))
                .thenReturn(false);
        //and
        var mockTaskGroupRepository = mock(TaskGroupRepository.class);
        when(mockTaskGroupRepository.findById(anyInt())).thenReturn(Optional.empty());
        //system under test
        var toTest = new TaskGroupService(mockTaskGroupRepository, mockTaskRepository);
        //when
        var exception = catchThrowable(() -> toTest.toggleGroup(1));
        //then
        assertThat(exception)
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("given id not found");
    }
    @Test
    @DisplayName("toggleGroup changes  taskGroup done")
    void toggleGroup_WorksAsExpected() {
        //given
        var mockTaskRepository = mock(TaskRepository.class);
        when(mockTaskRepository.existsByDoneIsFalseAndGroup_Id(anyInt()))
                .thenReturn(false);
        //and
        var taskGroup = new TaskGroup();
        var beforeToggle = taskGroup.isDone();

        // and
        var mockTaskGroupRepository = mock(TaskGroupRepository.class);
        when(mockTaskGroupRepository.findById(anyInt())).thenReturn(Optional.of(taskGroup));
        //system under test
        var toTest = new TaskGroupService(mockTaskGroupRepository, mockTaskRepository);
        //when
         toTest.toggleGroup(1);
         //then
        assertThat(taskGroup.isDone()).isNotEqualTo(beforeToggle);
    }
}