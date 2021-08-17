import { Test, TestingModule } from '@nestjs/testing';
import { UserService } from './user.service';
import { getRepositoryToken } from '@nestjs/typeorm';
import { User } from './entities/user.entity';
import { Repository } from 'typeorm';
import { CacheModule } from '../cache/cache.module';
import { CACHE_MANAGER } from '@nestjs/common';
import { UserNotFoundException } from './exceptions/user-not-found.exception';

const name = 'john';
const age = 12;

const user = new User(name, age);

const users = [user, new User('leonard', 12), new User('sheldon', 12)];

describe('UserService', () => {
  let service: UserService;
  let repo: Repository<User>;
  let cacheManager: any;
  beforeEach(async () => {
    const module: TestingModule = await Test.createTestingModule({
      imports: [CacheModule],
      providers: [
        UserService,
        {
          provide: getRepositoryToken(User),
          useValue: {
            create: jest.fn().mockReturnValue(user),
            save: jest.fn(),
            find: jest.fn().mockResolvedValue(users),
            findOne: jest.fn().mockResolvedValue(user),
            update: jest.fn().mockResolvedValue(true),
            delete: jest.fn().mockResolvedValue(true),
          },
        },
        {
          provide: CACHE_MANAGER,
          useValue: {
            store: {
              keys: jest.fn().mockReturnValue([]),
            },
          },
        },
      ],
    }).compile();

    service = module.get<UserService>(UserService);
    repo = module.get<Repository<User>>(getRepositoryToken(User));
    cacheManager = module.get<any>(CACHE_MANAGER);
  });

  it('should be defined', () => {
    expect(service).toBeDefined();
  });
  it('cache manager should be available', () => {
    expect(cacheManager).toBeDefined();
  });
  describe('create', () => {
    it('should successfully insert a user', () => {
      expect(
        service.create({
          name: name,
          age: age,
        }),
      ).resolves.toEqual(user);
      expect(repo.create).toBeCalledTimes(1);
      expect(repo.create).toBeCalledWith({
        name: name,
        age: age,
      });
      expect(repo.save).toBeCalledTimes(1);
    });
  });
  describe('findAll', () => {
    it('should return an array of users', async () => {
      const users = await service.findAll();
      expect(users).toEqual(users);
    });
  });
  describe('findOne', () => {
    it('should get a single user', () => {
      const repoSpy = jest.spyOn(repo, 'findOne');
      expect(service.findOne(1)).resolves.toEqual(user);
      expect(repoSpy).toBeCalledWith(1);
    });
    it('should get an exception', () => {
      const repoSpy = jest.spyOn(repo, 'findOne').mockResolvedValue(undefined);
      expect(service.findOne(2)).rejects.toThrow('User with id 2 not found');
      expect(repoSpy).toBeCalledWith(2);
      expect(repoSpy).toBeCalledTimes(1);
    });
  });
  describe('update', () => {
    it('should call the update method', async () => {
      const cat = await service.update(1, {
        name: name,
        age: 4,
      });
      expect(cat).toEqual(user);
      expect(repo.update).toBeCalledTimes(1);
      expect(repo.update).toBeCalledWith(1, { age: 4, name: 'john' });
    });
  });
  describe('remove', () => {
    it('should return success', () => {
      expect(service.remove(1)).resolves.toBeUndefined();
    });
    it('should return exception', () => {
      const repoSpy = jest.spyOn(repo, 'findOne').mockResolvedValue(undefined);
      expect(service.remove(2)).rejects.toThrow('User with id 2 not found');
      expect(repoSpy).toBeCalledWith(2);
      expect(repoSpy).toBeCalledTimes(1);
    });
  });
});
