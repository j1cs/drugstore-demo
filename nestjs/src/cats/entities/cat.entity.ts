import { Column, PrimaryGeneratedColumn, Entity } from 'typeorm';

@Entity()
export class Cat {
  @PrimaryGeneratedColumn()
  id: number;
  @Column()
  name: string;
  @Column()
  age: number;
  @Column()
  breed: string;
  @Column({ default: true })
  isActive: boolean;
}
