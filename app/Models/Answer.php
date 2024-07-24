<?php

namespace App\Models;

use Illuminate\Database\Eloquent\Factories\HasFactory;
use Illuminate\Database\Eloquent\Model;

class Answer extends Model
{
  protected $table = 'answers';

  public $timestamps = false;
  
  protected $fillable = ['answerId','questionId', 'answer', 'marks'];

  
}
