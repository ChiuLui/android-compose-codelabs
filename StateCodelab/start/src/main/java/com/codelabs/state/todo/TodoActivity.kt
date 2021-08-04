/*
 * Copyright (C) 2020 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.codelabs.state.todo

import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.material.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import com.codelabs.state.ui.StateCodelabTheme

/**
 * 主页
 * 逻辑写在 ViewModel
 * 界面写在 Compose，界面逻辑通过状态提升，提升到 Activity ，传入 ViewModel 逻辑。
 * 桥梁写在 Activity
 */
class TodoActivity : AppCompatActivity() {

    val todoViewModel by viewModels<TodoViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            StateCodelabTheme {
                Surface {
                    TodoActivityScreen(todoViewModel)
                }
            }
        }
    }

    @Composable
    private fun TodoActivityScreen(todoViewModel: TodoViewModel) {
        // .observeAsState observes a LiveData<T> and converts it into a State<T> object so Compose can react to value changes（观察 LiveData<T> 并将其转换为 State<T> 对象，以便 Compose 可以对值更改做出反应）
        val items: List<TodoItem> by todoViewModel.todoItems.observeAsState(initial = listOf())// listOf() 是给一个默认值，当返回为空，会给一个空数组

        TodoScreen(
            items = items,
            onAddItem = { todoViewModel.addItem(it) },
            onRemoveItem = { todoViewModel.removeItem(it) }
        )
    }

}
