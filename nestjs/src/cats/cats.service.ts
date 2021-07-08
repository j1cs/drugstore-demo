import { Injectable } from '@nestjs/common';
import { CreateCatDto } from './dto/create-cat.dto';
import { UpdateCatDto } from './dto/update-cat.dto';
import { InjectRepository } from '@nestjs/typeorm';
import { Cat } from './entities/cat.entity';
import { Repository } from 'typeorm';
import { CatNotFoundException } from './exceptions/cat-not-found.exception';

@Injectable()
export class CatsService {
  constructor(
    @InjectRepository(Cat)
    private readonly catRepository: Repository<Cat>,
  ) {}

  create(createCatDto: CreateCatDto): Promise<Cat> {
    const cat = this.catRepository.create(createCatDto);
    return this.catRepository.save(cat);
  }

  async findAll(): Promise<Cat[]> {
    return this.catRepository.find();
  }

  findOne(id: number): Promise<Cat> {
    const cat = this.catRepository.findOne(id);
    if (cat) {
      return cat;
    }
    throw new CatNotFoundException(id);
  }

  async update(id: number, updateCatDto: UpdateCatDto): Promise<Cat> {
    await this.catRepository.update(id, updateCatDto);
    const cat = this.catRepository.findOne(id);
    if (cat) {
      return cat;
    }
    throw new CatNotFoundException(id);
  }

  async remove(id: number): Promise<void> {
    const catDeleted = await this.catRepository.delete(id);
    if (!catDeleted.affected) {
      throw new CatNotFoundException(id);
    }
  }
}
