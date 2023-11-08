package com.x8bit.bitwarden.ui.tools.feature.send

import androidx.lifecycle.SavedStateHandle
import app.cash.turbine.test
import com.x8bit.bitwarden.ui.platform.base.BaseViewModelTest
import io.mockk.mockk
import kotlinx.coroutines.test.runTest
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test

class NewSendViewModelTest : BaseViewModelTest() {

    @Test
    fun `initial state should be correct`() {
        val viewModel = createViewModel()
        assertEquals(DEFAULT_STATE, viewModel.stateFlow.value)
    }

    @Test
    fun `initial state should read from saved state when present`() {
        val savedState = mockk<NewSendState>()
        val viewModel = createViewModel(
            savedStateHandle = SavedStateHandle(mapOf("state" to savedState)),
        )
        assertEquals(savedState, viewModel.stateFlow.value)
    }

    @Test
    fun `CloseClick should emit NavigateBack`() = runTest {
        val viewModel = createViewModel()
        viewModel.eventFlow.test {
            viewModel.trySendAction(NewSendAction.CloseClick)
            assertEquals(NewSendEvent.NavigateBack, awaitItem())
        }
    }

    @Test
    fun `SaveClick should emit ShowToast`() = runTest {
        val viewModel = createViewModel()
        viewModel.eventFlow.test {
            viewModel.trySendAction(NewSendAction.SaveClick)
            assertEquals(NewSendEvent.ShowToast("Save Not Implemented"), awaitItem())
        }
    }

    @Test
    fun `ChooseFileClick should emit ShowToast`() = runTest {
        val viewModel = createViewModel()
        viewModel.eventFlow.test {
            viewModel.trySendAction(NewSendAction.ChooseFileClick)
            assertEquals(NewSendEvent.ShowToast("Not Implemented: File Upload"), awaitItem())
        }
    }

    @Test
    fun `FileTypeClick and TextTypeClick should toggle sendType`() = runTest {
        val viewModel = createViewModel()
        viewModel.stateFlow.test {
            assertEquals(DEFAULT_STATE, awaitItem())
            viewModel.trySendAction(NewSendAction.FileTypeClick)
            assertEquals(
                DEFAULT_STATE.copy(selectedType = NewSendState.SendType.File),
                awaitItem(),
            )
            viewModel.trySendAction(NewSendAction.TextTypeClick)
            assertEquals(DEFAULT_STATE, awaitItem())
        }
    }

    @Test
    fun `NameChange should update name input`() = runTest {
        val viewModel = createViewModel()
        viewModel.stateFlow.test {
            assertEquals(DEFAULT_STATE, awaitItem())
            viewModel.trySendAction(NewSendAction.NameChange("input"))
            assertEquals(DEFAULT_STATE.copy(name = "input"), awaitItem())
        }
    }

    @Test
    fun `MaxAccessCountChange should update maxAccessCount`() = runTest {
        val viewModel = createViewModel()
        viewModel.stateFlow.test {
            assertEquals(DEFAULT_STATE, awaitItem())
            viewModel.trySendAction(NewSendAction.MaxAccessCountChange(5))
            assertEquals(DEFAULT_STATE.copy(maxAccessCount = 5), awaitItem())
        }
    }

    @Test
    fun `TextChange should update text input`() = runTest {
        val viewModel = createViewModel()
        viewModel.stateFlow.test {
            assertEquals(DEFAULT_STATE, awaitItem())
            viewModel.trySendAction(NewSendAction.TextChange("input"))
            assertEquals(
                DEFAULT_STATE.copy(
                    selectedType = NewSendState.SendType.Text(
                        input = "input",
                        isHideByDefaultChecked = false,
                    ),
                ), awaitItem(),
            )
        }
    }

    @Test
    fun `NoteChange should update note input`() = runTest {
        val viewModel = createViewModel()
        viewModel.stateFlow.test {
            assertEquals(DEFAULT_STATE, awaitItem())
            viewModel.trySendAction(NewSendAction.NoteChange("input"))
            assertEquals(DEFAULT_STATE.copy(noteInput = "input"), awaitItem())
        }
    }

    @Test
    fun `PasswordChange should update note input`() = runTest {
        val viewModel = createViewModel()
        viewModel.stateFlow.test {
            assertEquals(DEFAULT_STATE, awaitItem())
            viewModel.trySendAction(NewSendAction.PasswordChange("input"))
            assertEquals(DEFAULT_STATE.copy(passwordInput = "input"), awaitItem())
        }
    }

    @Test
    fun `DeactivateThisSendToggle should update isDeactivateChecked`() = runTest {
        val viewModel = createViewModel()
        viewModel.stateFlow.test {
            assertEquals(DEFAULT_STATE, awaitItem())
            viewModel.trySendAction(NewSendAction.DeactivateThisSendToggle(true))
            assertEquals(DEFAULT_STATE.copy(isDeactivateChecked = true), awaitItem())
        }
    }

    @Test
    fun `HideMyEmailToggle should update isHideEmailChecked`() = runTest {
        val viewModel = createViewModel()
        viewModel.stateFlow.test {
            assertEquals(DEFAULT_STATE, awaitItem())
            viewModel.trySendAction(NewSendAction.HideMyEmailToggle(true))
            assertEquals(DEFAULT_STATE.copy(isHideEmailChecked = true), awaitItem())
        }
    }

    private fun createViewModel(
        savedStateHandle: SavedStateHandle = SavedStateHandle(),
    ): NewSendViewModel = NewSendViewModel(
        savedStateHandle = savedStateHandle,
    )

    companion object {
        private val DEFAULT_STATE = NewSendState(
            name = "",
            maxAccessCount = null,
            passwordInput = "",
            noteInput = "",
            isHideEmailChecked = false,
            isDeactivateChecked = false,
            selectedType = NewSendState.SendType.Text(
                input = "",
                isHideByDefaultChecked = false,
            ),
        )
    }
}
