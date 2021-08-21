import { IsNotEmpty, IsNumber, IsString } from 'class-validator';

export class CreateUserDto {
  @IsString()
  @IsNotEmpty()
  name: string;
  @IsNumber()
  @IsNotEmpty()
  age: number;

  constructor(name?: string, age?: number) {
    this.name = name;
    this.age = age;
  }

  public toString(): string {
    return JSON.stringify(this);
  }
}
