<?php

namespace App\Models;

use Illuminate\Database\Eloquent\Factories\HasFactory;
use Illuminate\Database\Eloquent\Model;

class Answer extends Model
{

  public $timestamps = false;

  protected $table = 'answers';
  protected $fillable = ['answerId','questionId', 'answer', 'marks'];

  
}
