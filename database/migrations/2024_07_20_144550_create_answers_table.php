<?php

use Illuminate\Database\Migrations\Migration;
use Illuminate\Database\Schema\Blueprint;
use Illuminate\Support\Facades\Schema;

return new class extends Migration
{
    /**
     * Run the migrations.
     */
public function up(): void
{
    Schema::create('answers', function (Blueprint $table) {
    $table->id('answerId');
    $table->unsignedBigInteger('questionId');
    $table->text('answer');
    $table->integer('marks');


    $table->foreign('questionId')->references('questionId')->on('questions')->onDelete('cascade');
});
}

/**
 * Reverse the migrations.
 */
public function down(): void
{
    Schema::dropIfExists('answers');
}
};
