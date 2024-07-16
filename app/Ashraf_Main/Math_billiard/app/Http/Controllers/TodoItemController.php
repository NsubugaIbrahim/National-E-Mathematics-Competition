<?php

namespace App\Http\Controllers;

use Illuminate\Http\Request;
use App\Models\TodoItem;

class TodoItemController extends Controller
{
    public function index()
    {
        $todoItems = TodoItem::orderBy('created_at', 'desc')->get();
        return view('todo.index', compact('todoItems'));
    }

    public function store(Request $request)
    {
        $validated = $request->validate([
            'text' => 'required|string|max:255',
        ]);

        $todoItem = TodoItem::create([
            'text' => $validated['text'],
        ]);

        return response()->json($todoItem);
    }

    public function update(Request $request, TodoItem $todoItem)
    {
        $validated = $request->validate([
            'completed' => 'required|boolean',
        ]);

        $todoItem->update([
            'completed' => $validated['completed'],
        ]);

        return response()->json($todoItem);
    }

    public function destroy(TodoItem $todoItem)
    {
        $todoItem->delete();
        return response()->json(['message' => 'Todo item deleted successfully.']);
    }
}
