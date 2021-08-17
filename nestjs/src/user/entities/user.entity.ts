import { Column, Entity, PrimaryGeneratedColumn } from 'typeorm';

@Entity()
export class User {
  @PrimaryGeneratedColumn()
  id: number;
  @Column()
  name: string;
  @Column()
  age: number;
  @Column({ default: true })
  isActive: boolean;

  constructor(name?: string, age?: number) {
    this.name = name;
    this.age = age;
  }

  toString(): string {
    return JSON.stringify(this);
  }
}
