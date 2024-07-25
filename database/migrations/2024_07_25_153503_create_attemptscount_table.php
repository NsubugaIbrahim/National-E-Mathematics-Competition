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
        Schema::create('attemptsCounts', function (Blueprint $table) {
            $table->id('attemptsCountId');
            $table->unsignedBigInteger('participantId');
            $table->unsignedBigInteger('challengeId');
            $table->integer('score');
            $table->integer('correctAnswers');
            $table->integer('totalQuestions');
            $table->boolean('completed')->default(false);
            $table->timestamp('receivedAt')->useCurrent();

        });
    }

    /**
     * Reverse the migrations.
     */
    public function down(): void
    {
        Schema::dropIfExists('attemptsCounts');
    }
};
